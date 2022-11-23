package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.databinding.FragmentEditWorkoutDialogBinding
import hu.bp.mrtn.workoutdesigner.interfaces.EditWorkoutDialogClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel


class EditWorkoutDialogFragment(
    private val listener: EditWorkoutDialogClickInterface,
    private val workout_: WorkoutModel,
    private val position: Int) : DialogFragment() {


    private val workout = this.workout_.copy()
    private lateinit var binding: FragmentEditWorkoutDialogBinding




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentEditWorkoutDialogBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.etEditWorkoutName.setText(this.workout.workoutName)
        this.binding.etEditWorkoutDescription.setText(this.workout.workoutDescription)

        this.binding.btnEditWorkoutOk.setOnClickListener {

            // todo üreseket le kell kezelni
            this.workout.workoutName = this.binding.etEditWorkoutName.text.toString()
            this.workout.workoutDescription = this.binding.etEditWorkoutDescription.text.toString()

            listener.onSaveWorkoutClicked(this.workout, position)
            dismiss()
        }

        this.binding.btnEditWorkoutCancel.setOnClickListener { dismiss() }

    }


}