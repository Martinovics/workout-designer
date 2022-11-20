package hu.bp.mrtn.workoutdesigner.data

import androidx.room.Embedded
import androidx.room.Relation




data class WorkoutWithExercises(
    @Embedded val workout: WorkoutModel,
    @Relation(
        parentColumn = "workout_name",
        entityColumn = "workout_name"
    )
    val exercises: List<ExerciseModel>
)
