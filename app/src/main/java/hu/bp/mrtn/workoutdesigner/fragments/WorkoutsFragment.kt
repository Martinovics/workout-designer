package hu.bp.mrtn.workoutdesigner.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.MainActivity
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.adapters.WorkoutAdapter
import hu.bp.mrtn.workoutdesigner.data.*
import hu.bp.mrtn.workoutdesigner.databinding.FragmentWorkoutsBinding
import hu.bp.mrtn.workoutdesigner.interfaces.EditWorkoutDialogClickInterface
import hu.bp.mrtn.workoutdesigner.interfaces.WorkoutClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutDataViewModel
import kotlin.concurrent.thread


class WorkoutsFragment() : Fragment(), WorkoutClickInterface, EditWorkoutDialogClickInterface {


    private val TAG = "WorkoutsFragment"
    private var editModeOn = false
    private lateinit var binding: FragmentWorkoutsBinding
    private lateinit var adapter: WorkoutAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var db: DatabaseDao
    private val workoutDataViewModel: WorkoutDataViewModel by activityViewModels()




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
            this.addWorkout(WorkoutModel(workoutID=null, workoutName = this.adapter.genUniqueWorkoutName()))
            this.binding.rvWorkouts.scrollToPosition(this.adapter.itemCount - 1)
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
                            //this@WorkoutsFragment.itemTouchHelper.attachToRecyclerView(this@WorkoutsFragment.binding.rvWorkouts)
                        } else {
                            binding.btnAddWorkout.visibility = View.GONE
                           // this@WorkoutsFragment.itemTouchHelper.attachToRecyclerView(this@WorkoutsFragment.binding.rvWorkouts)
                        }
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
    }




    override fun onWorkoutAnywhereClicked(position: Int) {

        val workout = this.adapter.getWorkoutAt(position)

        if (this.editModeOn) {
            EditWorkoutDialogFragment(this, workout, position).show(parentFragmentManager, "asd")
        } else {
            //(activity as MainActivity).turnToExercisesPage()
            this.setWorkoutWithExercises(workout.workoutName)
        }
    }




    override fun onWorkoutAnywhereLongClicked(position: Int): Boolean {
        if (!this.editModeOn) {
            Log.d(TAG, "clicked on position=$position")
            return true
        }


        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete workout")
        builder.setMessage("You're about to delete: ${this.adapter.getWorkoutAt(position).workoutName}")
        builder.setPositiveButton("delete") { _: DialogInterface, _: Int ->
            this.removeWorkout(position)
        }
        builder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }
        builder.show()

        return true
    }




    override fun onSaveWorkoutClicked(updatedWorkout: WorkoutModel, position: Int) {  // todo itt még nincs color
        this.updateWorkout(updatedWorkout, position)
    }




    private fun loadWorkouts() {
        thread {
            val workouts = this.db.getWorkoutsWithExercises()

            val totalSeries = ArrayList<Int>()
            val totalSets = ArrayList<Int>()

            for (workout in workouts) {
                totalSeries.add(this.getTotalSeries(workout.exercises))
                totalSets.add(workout.exercises.size)
            }

            activity?.runOnUiThread {
                for (i in workouts.indices) {
                    this.adapter.addWorkout(workouts[i].workout, totalSeries[i], totalSets[i])
                }
            }
        }
    }




    private fun addWorkout(workout: WorkoutModel) {
        thread {
            workout.workoutIndex = this.adapter.itemCount  // nem kell -1, mert még ez után szúrjuk be
            workout.workoutID = this.db.insert(workout)

            activity?.runOnUiThread {
                this.adapter.addWorkout(workout)
                this.binding.rvWorkouts.scrollToPosition(this.adapter.itemCount - 1)
            }
        }
    }




    private fun updateWorkout(updatedWorkout: WorkoutModel, position: Int) {
        thread {
            this.db.update(updatedWorkout)

            activity?.runOnUiThread {
                this.adapter.updateWorkout(updatedWorkout, position)
            }
        }
    }




    private fun removeWorkout(position: Int) {
        thread {
            this.db.delete(this.adapter.getWorkoutAt(position))

            for (i in position until this.adapter.itemCount) {
                val workout = this.adapter.getWorkoutAt(i)
                workout.workoutIndex = i - 1
                this.db.updateWorkoutIndex(workout.workoutID!!, workout.workoutIndex)
            }

            activity?.runOnUiThread {
                this.adapter.removeWorkout(position)
            }
        }
    }




    private fun setWorkoutWithExercises(workoutName: String) {  // it also preloads data for exercises fragment
        thread {
            val workoutWithExercises = this.db.getWorkoutWithExercises(workoutName)
            if (workoutWithExercises != null) {

                val exercises = ArrayList<ExerciseModel>()
                for (exercise in workoutWithExercises.exercises) {
                    exercises.add(exercise)
                }

                this@WorkoutsFragment.workoutDataViewModel.workout = workoutWithExercises.workout
                this@WorkoutsFragment.workoutDataViewModel.exercises = exercises
                (activity as MainActivity).preloadExercises(this.workoutDataViewModel.exercises)
            }
        }
    }




    private fun getTotalSeries(exercises: List<ExerciseModel>): Int {
        val exerciseNames = ArrayList<String>()
        for (exercise in exercises) {
            exerciseNames.add(exercise.exerciseName)
        }
        return exerciseNames.distinct().size
    }




    fun updateTotalSeriesAndReps(totalSeries: Int, totalSets: Int, position: Int) {
        this.adapter.updateTotalSeriesAndReps(totalSeries, totalSets, position)
    }


}