package hu.bp.mrtn.workoutdesigner.models




data class WorkoutPreviewModel(
    var workoutName: String = "Workout",
    var totalSeries: Int = 0,
    var totalSets: Int = 0,
    var workoutDescription: String = "Description",
    var workoutColorHex: String = "#00346F"
)
