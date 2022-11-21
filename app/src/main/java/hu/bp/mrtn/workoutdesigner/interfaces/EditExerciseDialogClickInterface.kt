package hu.bp.mrtn.workoutdesigner.interfaces

interface EditExerciseDialogClickInterface {
    fun onSaveExerciseClicked(
        exerciseName: String,
        currWeight: Int,
        goalWeight: Int,
        currReps: Int,
        goalReps: Int,
        exerciseDescription: String,
        exerciseComment: String,
        position: Int)
}
