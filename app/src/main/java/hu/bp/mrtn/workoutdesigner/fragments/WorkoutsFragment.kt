package hu.bp.mrtn.workoutdesigner.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.MainActivity
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.adapters.WorkoutAdapter
import hu.bp.mrtn.workoutdesigner.data.DatabaseDao
import hu.bp.mrtn.workoutdesigner.data.WorkoutDatabase
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.databinding.FragmentWorkoutsBinding
import hu.bp.mrtn.workoutdesigner.interfaces.EditWorkoutDialogClickInterface
import hu.bp.mrtn.workoutdesigner.interfaces.ItemClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel
import kotlin.concurrent.thread


class WorkoutsFragment() : Fragment(), ItemClickInterface, EditWorkoutDialogClickInterface {


    private val TAG = "WorkoutsFragment"
    private var editModeOn = false
    private lateinit var binding: FragmentWorkoutsBinding
    private lateinit var adapter: WorkoutAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var db: DatabaseDao




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.db = WorkoutDatabase.getDatabase(requireContext()).getDao()

        this.initMenu()
        this.initRecycleView()
        this.initTouchHelper()

        this.loadWorkouts()


        binding.btnAddWorkout.visibility = View.GONE
        binding.btnAddWorkout.setOnClickListener {
            //this.adapter.addWorkout(WorkoutPreviewModel())
            //this.binding.rvWorkouts.scrollToPosition(this.adapter.itemCount - 1)
            this.addWorkout(WorkoutModel())
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

        // todo adatok betöltése a db-ből
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
            (activity as MainActivity).turnToExercisesPage()
            return
        }

        EditWorkoutDialogFragment(this, this.adapter.getWorkoutAt(position), position).show(parentFragmentManager, "asd")
    }




    override fun onItemLongClicked(position: Int): Boolean {
        if (!this.editModeOn) {
            Log.d(TAG, "clicked on position=$position")
            return true
        }


        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete workout")
        builder.setMessage("You're about to delete: ${this.adapter.getWorkoutAt(position).workoutName}")
        builder.setPositiveButton("delete") { _: DialogInterface, _: Int ->
            this.adapter.removeWorkout(position)
            // todo ilyenkor frissíteni kell az osszes workout indexét, a sorrendezés miatt
        }
        builder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }
        builder.show()

        return true
    }




    override fun onSaveWorkoutClicked(workout_name: String, workout_description: String, position: Int) {
        Log.d(TAG, "data from dialog:  $workout_name $workout_description $position")
        this.adapter.setWorkoutName(workout_name, position)
        this.adapter.setWorkoutDescription(workout_description, position)
    }




    private fun addWorkout(workout: WorkoutModel) {
        thread {
            this.db.insert(workout)

            activity?.runOnUiThread {
                this.adapter.addWorkout(WorkoutPreviewModel())
                this.binding.rvWorkouts.scrollToPosition(this.adapter.itemCount - 1)
            }
        }
    }




    private fun loadWorkouts() {
        thread {
            val workouts = this.db.getWorkouts()

            activity?.runOnUiThread {
                for (workout in workouts) {

                    val workoutPrev = WorkoutPreviewModel(
                        workoutName = workout.workout.workoutName,
                        workoutDescription = workout.workout.workoutDescription,
                        workoutColorHex = workout.workout.workoutColorHex,
                        totalSeries = workout.exercises.size,
                        totalSets = workout.exercises.size
                    )

                    this.adapter.addWorkout(workoutPrev)
                }
            }
        }
    }


}