package app.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_runs")
data class DeletedRunSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val runId: String,
    val userId: String,
)
