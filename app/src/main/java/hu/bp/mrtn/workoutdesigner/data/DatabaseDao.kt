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



}