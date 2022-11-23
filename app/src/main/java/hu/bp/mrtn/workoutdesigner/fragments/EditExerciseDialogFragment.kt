package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.databinding.FragmentEditExerciseDialogBinding
import hu.bp.mrtn.workoutdesigner.interfaces.EditExerciseDialogClickInterface






class EditExerciseDialogFragment(
    private val listener: EditExerciseDialogClickInterface,
    private val exercise_: ExerciseModel,
    private val position: Int) : DialogFragment() {


    private val exercise = this.exercise_.copy()
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

            // todo üreseket le kell kezelni
            this.exercise.exerciseName = this.binding.etEditExerciseName.text.toString()
            this.exercise.currWeight = this.binding.etEditExerciseCurrWeight.text.toString().toInt()
            this.exercise.goalWeight = this.binding.etEditExerciseGoalWeight.text.toString().toInt()
            this.exercise.currReps = this.binding.etEditExerciseCurrReps.text.toString().toInt()
            this.exercise.goalReps = this.binding.etEditExerciseGoalReps.text.toString().toInt()
            this.exercise.exerciseDescription = this.binding.etEditExerciseDescription.text.toString()
            this.exercise.exerciseComment = this.binding.etEditExerciseComment.text.toString()

            listener.onSaveExerciseClicked(this.exercise, this.position)
            dismiss()
        }

        this.binding.btnEditExerciseCancel.setOnClickListener { dismiss() }


    }


}