package hu.bp.mrtn.workoutdesigner.models

import androidx.lifecycle.ViewModel
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutWithExercises


class WorkoutDataViewModel: ViewModel() {
    var workout = WorkoutModel(0, "")
    var exercises = ArrayList<ExerciseModel>()
}