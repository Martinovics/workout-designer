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
    fun getWorkouts(): List<WorkoutWithExercises>


//    @Query("DELETE FROM workouts WHERE workout_name = :workoutName")
//    fun deleteWorkout(workoutName: String)
//
//
//    @Query("SELECT * FROM workouts WHERE workout_name = :workoutName LIMIT 1")
//    fun getWorkoutModel(workoutName: String): WorkoutModel


    @Query("SELECT * FROM workouts")
    fun getWorkoutModels(): List<WorkoutModel>

}