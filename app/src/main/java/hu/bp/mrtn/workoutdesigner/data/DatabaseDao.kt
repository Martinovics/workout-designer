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




    @Insert
    fun insert(exercise: ExerciseModel): Long


    @Update
    fun update(exercise: ExerciseModel)


    @Delete
    fun delete(exercise: ExerciseModel)
}