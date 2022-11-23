package hu.bp.mrtn.workoutdesigner.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bp.mrtn.workoutdesigner.MainActivity
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.adapters.ExerciseAdapter
import hu.bp.mrtn.workoutdesigner.data.DatabaseDao
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutDatabase
import hu.bp.mrtn.workoutdesigner.databinding.EditNumberBinding
import hu.bp.mrtn.workoutdesigner.databinding.FragmentExercisesBinding
import hu.bp.mrtn.workoutdesigner.interfaces.ExerciseClickInterface
import hu.bp.mrtn.workoutdesigner.interfaces.EditExerciseDialogClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutDataViewModel
import kotlin.concurrent.thread


class ExercisesFragment : Fragment(), ExerciseClickInterface, EditExerciseDialogClickInterface {


    private val logTag = "ExercisesFragment"
    private var editModeOn = false
    private lateinit var binding: FragmentExercisesBinding
    private lateinit var adapter: ExerciseAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var db: DatabaseDao
    private val workoutDataViewModel: WorkoutDataViewModel by activityViewModels()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(logTag, "onCreateView")
        this.binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.db = WorkoutDatabase.getDatabase(requireContext()).getDao()

        this.initMenu()
        this.initRecycleView()
        // this.initTouchHelper()


        binding.btnAddExercise.visibility = View.GONE
        binding.btnAddExercise.setOnClickListener {
            this.addExercise(ExerciseModel(workoutID = workoutDataViewModel.workout.workoutID), scrollToLast = true)
        }

        if (this.adapter.itemCount == 0) {
            this.binding.tvAddExerciseHint.visibility = View.VISIBLE
        } else {
            this.binding.tvAddExerciseHint.visibility = View.GONE
        }

    }




    override fun onResume() {
        super.onResume()

        // this.loadExercises()
    }




    override fun onPause() {  // itt vissza kell állítani, úgy mintha nem lennénk szerkesztő módban
        super.onPause()

        this.editModeOn = false
        binding.btnAddExercise.visibility = View.GONE
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
                            binding.btnAddExercise.visibility = View.VISIBLE
                            //this@WorkoutsFragment.itemTouchHelper.attachToRecyclerView(this@WorkoutsFragment.binding.rvWorkouts)
                        } else {
                            binding.btnAddExercise.visibility = View.GONE
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
        this.adapter = ExerciseAdapter(this)
        this.binding.rvExercises.adapter = this.adapter
        this.binding.rvExercises.layoutManager = LinearLayoutManager(requireContext())
    }




    private fun initTouchHelper() { }




    override fun onExerciseWeightBoxClicked(position: Int) {
        val exercise = this.adapter.getExerciseAt(position)

        if (this.editModeOn) {
            this.showExerciseEditDialog(position)

        } else {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Current weight")

            val etBinding = EditNumberBinding.inflate(layoutInflater)
            etBinding.etEditNumber.setText(exercise.currReps.toString())
            builder.setView(etBinding.root)

            builder.setPositiveButton("ok") { _: DialogInterface, _: Int ->

                if (etBinding.etEditNumber.text.isEmpty()) {
                    Toast.makeText(this.context, "Cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                exercise.currWeight = etBinding.etEditNumber.text.toString().toInt()
                this.updateExercise(exercise, position)
            }

            builder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }

            builder.show()

        }
    }




    override fun onExerciseRepsBoxClicked(position: Int) {
        val exercise = this.adapter.getExerciseAt(position)

        if (this.editModeOn) {
            this.showExerciseEditDialog(position)

        } else {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Current reps")

            val etBinding = EditNumberBinding.inflate(layoutInflater)
            etBinding.etEditNumber.setText(exercise.currReps.toString())
            builder.setView(etBinding.root)

            builder.setPositiveButton("ok") { _: DialogInterface, _: Int ->

                if (etBinding.etEditNumber.text.isEmpty()) {
                    Toast.makeText(this.context, "Cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                exercise.currReps = etBinding.etEditNumber.text.toString().toInt()
                this.updateExercise(exercise, position)
            }

            builder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }

            builder.show()
        }
    }




    override fun onExerciseAnywhereClicked(position: Int) {
        if (!this.editModeOn) {
            return
        }

        this.showExerciseEditDialog(position)
    }




    override fun onExerciseAnywhereLongClicked(position: Int): Boolean {
        if (!this.editModeOn) {
            return true
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete exercise")
        builder.setMessage("You're about to delete: ${this.adapter.getExerciseAt(position).exerciseName}")
        builder.setPositiveButton("delete") { _: DialogInterface, _: Int ->
            this.removeExercise(position)
        }
        builder.setNegativeButton("cancel") { _: DialogInterface, _: Int -> }
        builder.show()

        return true
    }




    override fun onSaveExerciseClicked(updatedExercise: ExerciseModel, position: Int) {
        this.updateExercise(updatedExercise, position)
    }




    fun preloadExercises(exercises: ArrayList<ExerciseModel>) {
        thread {
            activity?.runOnUiThread {

                this.adapter.clearExercises()
                for ((i, exercise) in exercises.withIndex()) {
                    // this.adapter.addExercise(exercise, delayMillis = i * 100L)
                    this.adapter.addExercise(exercise)
                }

                if (this.adapter.itemCount == 0) {
                    this.binding.tvAddExerciseHint.visibility = View.VISIBLE
                } else {
                    this.binding.tvAddExerciseHint.visibility = View.GONE
                }
            }
        }
    }




    private fun loadExercises() {
        if (workoutDataViewModel.workout.workoutID == null || workoutDataViewModel.workout.workoutID == 0L) {
            Log.i(logTag, "Choose a workout before adding a new exercise")
            return
        }

        thread {
            activity?.runOnUiThread {
                this.adapter.clearExercises()
                for (exercise in workoutDataViewModel.exercises) {
                    this.adapter.addExercise(exercise)
                }
            }
        }
    }




    private fun addExercise(exercise: ExerciseModel, scrollToLast: Boolean = false) {
        if (exercise.workoutID == null || exercise.workoutID == 0L) {
            Log.i(logTag, "Choose a workout before adding a new exercise")
            return
        }

        thread {
            exercise.exerciseIndex = this.adapter.itemCount  // nem kell -1, mert még ez után szúrjuk be
            exercise.exerciseID = this.db.insert(exercise)

            activity?.runOnUiThread {
                this.adapter.addExercise(exercise)
                (activity as MainActivity).updateTotalSeriesAndReps(
                    this.adapter.getTotalSeries(),
                    this.adapter.itemCount,
                    this.workoutDataViewModel.workout.workoutIndex
                )
                this.binding.tvAddExerciseHint.visibility = View.GONE

                if (scrollToLast) {  // itt kell mert a thread miatt lefutna ez a sor hamrabb és akkor csak az utolsó előttihez görgetne
                    this.binding.rvExercises.scrollToPosition(this.adapter.itemCount - 1)
                }
            }
        }
    }




    private fun updateExercise(updatedExercise: ExerciseModel, position: Int) {
        thread {
            this.db.update(updatedExercise)

            activity?.runOnUiThread {
                this.adapter.updateExercise(updatedExercise, position)
                (activity as MainActivity).updateTotalSeriesAndReps(
                    this.adapter.getTotalSeries(),
                    this.adapter.itemCount,
                    this.workoutDataViewModel.workout.workoutIndex
                )
            }
        }
    }




    private fun removeExercise(position: Int) {
        thread {
            this.db.delete(this.adapter.getExerciseAt(position))

            for (i in position until this.adapter.itemCount) {
                val exercise = this.adapter.getExerciseAt(i)
                exercise.exerciseIndex = i - 1
                this.db.updateExerciseIndex(exercise.exerciseID!!, exercise.exerciseIndex)
            }

            activity?.runOnUiThread {
                this.adapter.removeExercise(position)
                (activity as MainActivity).updateTotalSeriesAndReps(
                    this.adapter.getTotalSeries(),
                    this.adapter.itemCount,
                    this.workoutDataViewModel.workout.workoutIndex
                )
                if (this.adapter.itemCount == 0) {
                    this.binding.tvAddExerciseHint.visibility = View.VISIBLE
                }
            }
        }
    }




    private fun showExerciseEditDialog(position: Int) {
        val exercise = this.adapter.getExerciseAt(position)
        EditExerciseDialogFragment(this, exercise, position).show(parentFragmentManager, "asd")
    }


}