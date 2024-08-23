package app.core.data.run

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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class RunRepositoryImpl(
    private val localRunDatasource: LocalRunDatasource,
    private val remoteRunDatasource: RemoteRunDatasource,
    private val applicationScope: CoroutineScope,
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

        val remoteResult = applicationScope.async {
            remoteRunDatasource.deleteRun(id)
        }.await()
    }
}