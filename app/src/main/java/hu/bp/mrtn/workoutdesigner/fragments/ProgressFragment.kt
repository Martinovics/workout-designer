package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bp.mrtn.workoutdesigner.MainActivity
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutDatabase
import hu.bp.mrtn.workoutdesigner.databinding.FragmentExercisesBinding
import hu.bp.mrtn.workoutdesigner.databinding.FragmentProgressBinding


class ProgressFragment : Fragment() {


    private val logTag = "ProgressFragment"
    private lateinit var binding: FragmentProgressBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.button.setOnClickListener { Log.d(logTag, "btn clicked") }
    }


}