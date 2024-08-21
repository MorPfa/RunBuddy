package app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.core.database.dao.RunDao
import app.core.database.entity.RunEntity

@Database(entities = [RunEntity::class], version = 1)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
}