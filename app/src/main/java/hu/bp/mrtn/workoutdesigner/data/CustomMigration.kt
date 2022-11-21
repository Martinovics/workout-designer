package hu.bp.mrtn.workoutdesigner.data

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec




@DeleteColumn(
    tableName = "exercises",
    columnName = "workout_name"
)
@RenameColumn(
    tableName = "exercises",
    fromColumnName = "id",
    toColumnName = "exercise_id"
)
@RenameColumn(
    tableName = "workouts",
    fromColumnName = "id",
    toColumnName = "workout_id"
)
class CustomMigration: AutoMigrationSpec {
}