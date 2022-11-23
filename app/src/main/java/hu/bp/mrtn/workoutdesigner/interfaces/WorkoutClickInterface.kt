package hu.bp.mrtn.workoutdesigner.interfaces


interface WorkoutClickInterface {
    fun onWorkoutAnywhereClicked(position: Int)
    fun onWorkoutAnywhereLongClicked(position: Int): Boolean
}
