package app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.core.database.dao.RunDao
import app.core.database.dao.RunPendingSyncDao
import app.core.database.entity.DeletedRunSyncEntity
import app.core.database.entity.RunEntity
import app.core.database.entity.RunPendingSyncEntity

@Database(entities = [
    RunEntity::class,
    RunPendingSyncEntity::class,
    DeletedRunSyncEntity::class
                     ],
    version = 2)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao

    abstract val runPendingSyncDao : RunPendingSyncDao
}