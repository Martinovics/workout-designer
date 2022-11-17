package hu.bp.mrtn.workoutdesigner.models




data class WorkoutPreviewModel(
    var workoutName: String = "Workout",
    var setsCount: Int = 0,
    var workoutDescription: String = "Description"
)
