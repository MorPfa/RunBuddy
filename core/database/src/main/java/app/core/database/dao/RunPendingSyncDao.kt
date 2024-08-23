package app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import app.core.database.entity.DeletedRunSyncEntity
import app.core.database.entity.RunPendingSyncEntity

@Dao
interface RunPendingSyncDao {

    //CREATED RUNS
    @Query("SELECT * FROM runs_pending WHERE userId = :userId")
    suspend fun getAllRunPendingSyncEntities(userId: String): List<RunPendingSyncEntity>
    @Query("SELECT * FROM runs_pending WHERE runId = :runId")
    suspend fun getRunPendingSyncEntity(runId: String): RunPendingSyncEntity?
    @Upsert
    suspend fun upsertRunPendingSyncEntities(entity: RunPendingSyncEntity)
    @Query("DELETE FROM runs_pending WHERE runId = :runId")
    suspend fun deleteRunPendingSyncEntity(runId: String)


    //DELETED RUNS
    @Query("SELECT * FROM deleted_runs WHERE userId = :userId")
    suspend fun getAllDeletedRunSyncEntities(userId: String): List<DeletedRunSyncEntity>
    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeletedRunSyncEntity)
    @Query("DELETE FROM deleted_runs WHERE runID = :runId")
    suspend fun deleteDeletedRunSyncEntity(runId: String)
}