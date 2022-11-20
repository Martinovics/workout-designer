package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bp.mrtn.workoutdesigner.databinding.FragmentEditWorkoutDialogBinding
import hu.bp.mrtn.workoutdesigner.interfaces.EditWorkoutDialogClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel


class EditWorkoutDialogFragment(
    private val listener: EditWorkoutDialogClickInterface, private val workout: WorkoutPreviewModel, private val position: Int) : DialogFragment() {

    // a workout_name és a workout_description-t azért adjuk át, hogy előre ki tudjuk tölteni a textboxot
    // a position meg azért kell, hogy tudjuk, melyik gyakorlatot kell frissíteni


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
            listener.onSaveWorkoutClicked(
                this.binding.etEditWorkoutName.text.toString(),
                this.binding.etEditWorkoutDescription.text.toString(),
                this.position
            )
            dismiss()
        }

        this.binding.btnEditWorkoutCancel.setOnClickListener { dismiss() }

    }


}