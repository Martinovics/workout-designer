package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.databinding.FragmentExercisesBinding




class ExercisesFragment : Fragment() {


    private val TAG = "ExercisesFragment"
    private lateinit var binding: FragmentExercisesBinding




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.toolbar_edit -> {
                Log.d(TAG, "toggle edit")
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }



}