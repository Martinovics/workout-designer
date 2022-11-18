package hu.bp.mrtn.workoutdesigner.interfaces


interface EditWorkoutDialogClickInterface {
    fun onSaveWorkoutClicked(workout_name: String, workout_description: String, position: Int)
}
