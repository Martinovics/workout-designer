package hu.bp.mrtn.workoutdesigner.data

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec




@RenameColumn(
    tableName = "workouts",
    fromColumnName = "index",
    toColumnName = "exercise_index"
)
@RenameColumn(
    tableName = "workouts",
    fromColumnName = "description",
    toColumnName = "exercise_description"
)
class CustomMigration: AutoMigrationSpec {
}