package hu.bp.mrtn.workoutdesigner.data

import androidx.room.Embedded
import androidx.room.Relation




data class WorkoutWithExercises(
    @Embedded val workout: WorkoutModel,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "workout_id"
    )
    val exercises: List<ExerciseModel>
)
