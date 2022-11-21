package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.databinding.FragmentEditExerciseDialogBinding
import hu.bp.mrtn.workoutdesigner.databinding.FragmentEditWorkoutDialogBinding
import hu.bp.mrtn.workoutdesigner.interfaces.EditExerciseDialogClickInterface
import hu.bp.mrtn.workoutdesigner.interfaces.EditWorkoutDialogClickInterface


class EditExerciseDialogFragment(
    private val listener: EditExerciseDialogClickInterface, private val exercise: ExerciseModel, private val position: Int) : DialogFragment() {




    private lateinit var binding: FragmentEditExerciseDialogBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentEditExerciseDialogBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.etEditExerciseName.setText(this.exercise.exerciseName)
        this.binding.etEditExerciseCurrWeight.setText(this.exercise.currWeight.toString())
        this.binding.etEditExerciseGoalWeight.setText(this.exercise.goalWeight.toString())
        this.binding.etEditExerciseCurrReps.setText(this.exercise.currReps.toString())
        this.binding.etEditExerciseGoalReps.setText(this.exercise.goalReps.toString())
        this.binding.etEditExerciseDescription.setText(this.exercise.exerciseDescription)
        this.binding.etEditExerciseComment.setText(this.exercise.exerciseComment)


        this.binding.btnEditExerciseOk.setOnClickListener {
            listener.onSaveExerciseClicked(
                this.binding.etEditExerciseName.text.toString(),
                this.binding.etEditExerciseCurrWeight.text.toString().toInt(),
                this.binding.etEditExerciseGoalWeight.text.toString().toInt(),
                this.binding.etEditExerciseCurrReps.text.toString().toInt(),
                this.binding.etEditExerciseGoalReps.text.toString().toInt(),
                this.binding.etEditExerciseDescription.text.toString(),
                this.binding.etEditExerciseComment.text.toString(),
                this.position
            )
            dismiss()
        }

        this.binding.btnEditExerciseOk.setOnClickListener { dismiss() }

    }


}