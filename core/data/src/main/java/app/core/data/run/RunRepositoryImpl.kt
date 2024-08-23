package app.core.data.run

import app.core.database.dao.RunPendingSyncDao
import app.core.database.mappers.toRun
import app.core.domain.SessionStorage
import app.core.domain.run.LocalRunDatasource
import app.core.domain.run.RemoteRunDatasource
import app.core.domain.run.Run
import app.core.domain.run.RunId
import app.core.domain.run.RunRepository
import app.core.domain.util.DataError
import app.core.domain.util.EmptyResult
import app.core.domain.util.Result
import app.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RunRepositoryImpl(
    private val localRunDatasource: LocalRunDatasource,
    private val remoteRunDatasource: RemoteRunDatasource,
    private val applicationScope: CoroutineScope,
    private val runPendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
) : RunRepository {


    override fun getRuns(): Flow<List<Run>> {
        return localRunDatasource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when (val result = remoteRunDatasource.getRuns()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localRunDatasource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        val localResult = localRunDatasource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDatasource.postRun(
            run = runWithId,
            mapPicture = mapPicture
        )

        return when (remoteResult) {
            is Result.Error -> {
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localRunDatasource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDatasource.deleteRun(id)

        // Edge case where run is created in offline-mode,
        // and deleted in offline mode as well in which case
        // we don't need to sync anything
        val isPendingSync = runPendingSyncDao.getRunPendingSyncEntity(id) != null
        if (isPendingSync) {
            runPendingSyncDao.deleteDeletedRunSyncEntity(id)
            return
        }

        val remoteResult = applicationScope.async {
            remoteRunDatasource.deleteRun(id)
        }.await()
    }

    override suspend fun syncPendingRuns() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val createdRuns = async {
                runPendingSyncDao.getAllRunPendingSyncEntities(userId)
            }
            val deletedRuns = async {
                runPendingSyncDao.getAllDeletedRunSyncEntities(userId)
            }
            val createJobs = createdRuns.await().map {
                launch {
                    val run = it.run.toRun()
                    when (remoteRunDatasource.postRun(run, it.mapPictureBytes)) {
                        is Result.Error -> Unit
                        is Result.Success -> {
                            applicationScope.launch {
                                runPendingSyncDao.deleteRunPendingSyncEntity(it.runId)
                            }.join()
                        }
                    }
                }
            }
            val deleteJobs = deletedRuns.await().map {
                launch {
                    when (remoteRunDatasource.deleteRun(it.runID)) {
                        is Result.Error -> Unit
                        is Result.Success -> {
                            applicationScope.launch {
                                runPendingSyncDao.deleteDeletedRunSyncEntity(it.runID)
                            }.join()
                        }
                    }
                }
            }
            createJobs.forEach { it.join() }
            deleteJobs.forEach { it.join() }
        }
    }
}