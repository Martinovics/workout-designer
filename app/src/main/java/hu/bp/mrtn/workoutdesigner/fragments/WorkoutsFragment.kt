package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.databinding.FragmentExercisesBinding
import hu.bp.mrtn.workoutdesigner.databinding.FragmentWorkoutsBinding


class WorkoutsFragment : Fragment() {


    private val TAG = "WorkoutsFragment"
    private lateinit var binding: FragmentWorkoutsBinding




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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