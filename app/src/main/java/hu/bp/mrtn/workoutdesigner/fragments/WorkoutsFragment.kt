package hu.bp.mrtn.workoutdesigner.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.adapters.WorkoutAdapter
import hu.bp.mrtn.workoutdesigner.databinding.FragmentExercisesBinding
import hu.bp.mrtn.workoutdesigner.databinding.FragmentWorkoutsBinding
import hu.bp.mrtn.workoutdesigner.interfaces.ItemClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutModel
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel
import java.util.Collections


class WorkoutsFragment() : Fragment(), ItemClickInterface {


    private val TAG = "WorkoutsFragment"
    private var editModeOn = false
    private lateinit var binding: FragmentWorkoutsBinding
    private lateinit var adapter: WorkoutAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initMenu()
        this.initRecycleView()
        this.initTouchHelper()


        binding.btnAddWorkout.visibility = View.GONE
        binding.btnAddWorkout.setOnClickListener {
            this.adapter.addWorkout(WorkoutModel())
        }

    }




    override fun onPause() {  // itt vissza kell állítani, úgy mintha nem lennénk szerkesztő módban
        super.onPause()

        this.editModeOn = false
        binding.btnAddWorkout.visibility = View.GONE
    }




    private fun initMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {


            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.toolbar_edit -> {

                        editModeOn = !editModeOn

                        if (editModeOn) {  // a szerkesztő gomb hátterét is be lehetne állítani
                            binding.btnAddWorkout.visibility = View.VISIBLE
                        } else {
                            binding.btnAddWorkout.visibility = View.GONE
                        }



                        // megjelenítjük a floating buttont
                        // az adapterben újraállítjuk a listenereket

                        Log.d(TAG, "toggle edit")
                        return true
                    }
                }
                return false
            }


        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }




    private fun initRecycleView() {
        this.adapter = WorkoutAdapter(this)
        this.binding.rvWorkouts.adapter = this.adapter
        this.binding.rvWorkouts.layoutManager = LinearLayoutManager(requireContext())
    }




    private fun initTouchHelper() {
        val simpleCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0) {


            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                this@WorkoutsFragment.adapter.swapWorkouts(fromPos, toPos)
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        }


        this.itemTouchHelper = ItemTouchHelper(simpleCallback)
        this.itemTouchHelper.attachToRecyclerView(this.binding.rvWorkouts)
    }





    override fun onItemClicked(position: Int) {
        if (!this.editModeOn) {
            Log.d(TAG, "load new workout")
        } else {
            Log.d(TAG, "edit workout")
        }
    }

    override fun onItemLongClicked(position: Int): Boolean {
        if (!this.editModeOn) {
            return true
        }


        Log.d(TAG, "delete workout")


        return true
    }


}