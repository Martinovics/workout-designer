package hu.bp.mrtn.workoutdesigner.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "workouts")
data class WorkoutModel(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "workout_name") var workoutName: String,
    @ColumnInfo(name = "workout_index", defaultValue = "0") var workoutIndex: Int = 0,
    @ColumnInfo(name = "workout_color_hex", defaultValue = "#01284a") var workoutColorHex: String = "#01284a",
    @ColumnInfo(name = "workout_description", defaultValue = "Description") var workoutDescription: String = "Description"
)
