package hu.bp.mrtn.workoutdesigner.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "workouts")
data class ExerciseModel(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,

    @ColumnInfo(name = "workout_name", defaultValue = "Workout") var workoutName: String = "Workout",
    @ColumnInfo(name = "workout_index", defaultValue = "0") var workoutIndex: Int = 0,
    @ColumnInfo(name = "workout_description", defaultValue = "Description") var workoutDescription: String = "Description",
    @ColumnInfo(name = "workout_color_hex", defaultValue = "#01284a") var workoutColorHex: String = "#01284a",

    @ColumnInfo(name = "exercise_name", defaultValue = "Exercise") var exerciseName: String = "Exercise",
    @ColumnInfo(name = "exercise_index", defaultValue = "0") var exerciseIndex: Int = 0,
    @ColumnInfo(name = "exercise_color_hex", defaultValue = "#01284a")  var exerciseColorHex: String = "#01284a",
    @ColumnInfo(name = "exercise_description", defaultValue = "Description")  var exerciseDescription: String = "Description",
    @ColumnInfo(name = "exercise_comment", defaultValue = "Comment")  var exerciseComment: String = "Comment",
    @ColumnInfo(name = "curr_reps", defaultValue = "0") var currReps: Int = 0,
    @ColumnInfo(name = "goal_reps", defaultValue = "0") var goalReps: Int = 0,
    @ColumnInfo(name = "curr_weight", defaultValue = "0") var currWeight: Int = 0,
    @ColumnInfo(name = "goal_weight", defaultValue = "0") var goalWeight: Int = 0,
    @ColumnInfo(name = "muscle_group", defaultValue = "Muscle group")  var muscleGroup: String = "Muscle group",
    @ColumnInfo(name = "muscle_part", defaultValue = "Muscle part")  var musclePart: String = "Muscle part"

)