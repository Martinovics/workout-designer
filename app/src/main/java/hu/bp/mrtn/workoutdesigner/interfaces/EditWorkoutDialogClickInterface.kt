package hu.bp.mrtn.workoutdesigner.interfaces

import hu.bp.mrtn.workoutdesigner.data.WorkoutModel


interface EditWorkoutDialogClickInterface {
    fun onSaveWorkoutClicked(updatedWorkout: WorkoutModel, position: Int)
}
