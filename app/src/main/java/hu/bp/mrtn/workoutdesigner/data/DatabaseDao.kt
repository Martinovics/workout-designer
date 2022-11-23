package hu.bp.mrtn.workoutdesigner.data

import androidx.room.*


@Dao
interface DatabaseDao {


    @Insert
    fun insert(workout: WorkoutModel): Long

    @Update
    fun update(workout: WorkoutModel)

    @Delete
    fun delete(workout: WorkoutModel)


    @Query("SELECT * FROM workouts")
    fun getWorkoutModels(): List<WorkoutModel>


    @Query("SELECT * FROM workouts WHERE workout_name = :workoutName")
    fun getWorkoutWithExercises(workoutName: String): WorkoutWithExercises?


    @Query("UPDATE workouts SET workout_index = :workoutIndex WHERE workout_id = :workoutID")
    fun updateWorkoutIndex(workoutID: Long, workoutIndex: Int)




    @Insert
    fun insert(exercise: ExerciseModel): Long


    @Update
    fun update(exercise: ExerciseModel)


    @Delete
    fun delete(exercise: ExerciseModel)


    @Query("UPDATE exercises SET exercise_index = :exerciseIndex WHERE exercise_id = :exerciseID")
    fun updateExerciseIndex(exerciseID: Long, exerciseIndex: Int)
}