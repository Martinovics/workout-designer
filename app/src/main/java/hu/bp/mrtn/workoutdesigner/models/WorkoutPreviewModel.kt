package hu.bp.mrtn.workoutdesigner.models

import hu.bp.mrtn.workoutdesigner.data.WorkoutModel




data class WorkoutPreviewModel(
    var workout: WorkoutModel = WorkoutModel(),
    var totalSeries: Int = 0,
    var totalSets: Int = 0,
)
