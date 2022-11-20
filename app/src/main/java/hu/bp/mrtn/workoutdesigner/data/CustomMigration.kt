package hu.bp.mrtn.workoutdesigner.data

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec




@DeleteColumn(
    tableName="workouts",
    columnName="id"
)
class CustomMigration: AutoMigrationSpec {
}