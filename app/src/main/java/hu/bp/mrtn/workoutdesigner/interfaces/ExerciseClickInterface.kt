package hu.bp.mrtn.workoutdesigner.interfaces


interface ExerciseClickInterface {
    fun onExerciseWeightBoxClicked(position: Int)  // to set current weight
    fun onExerciseRepsBoxClicked(position: Int)    // to set current reps
    fun onExerciseAnywhereClicked(position: Int)
    fun onExerciseAnywhereLongClicked(position: Int): Boolean
}
