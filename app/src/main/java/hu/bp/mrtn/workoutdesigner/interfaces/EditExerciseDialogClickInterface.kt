package hu.bp.mrtn.workoutdesigner.interfaces

import hu.bp.mrtn.workoutdesigner.data.ExerciseModel




interface EditExerciseDialogClickInterface {
    fun onSaveExerciseClicked(updatedExercise: ExerciseModel, position: Int)
}
