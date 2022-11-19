package hu.bp.mrtn.workoutdesigner.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update




interface ExerciseModelDao {

    @Insert
    fun insert(exercise: ExerciseModel): Long


    @Update
    fun update(exercise: ExerciseModel)


    @Delete
    fun delete(exercise: ExerciseModel)


}