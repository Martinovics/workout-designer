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
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bp.mrtn.workoutdesigner.R
import hu.bp.mrtn.workoutdesigner.adapters.ExerciseAdapter
import hu.bp.mrtn.workoutdesigner.data.DatabaseDao
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutDatabase
import hu.bp.mrtn.workoutdesigner.databinding.EditNumberBinding
import hu.bp.mrtn.workoutdesigner.databinding.FragmentExercisesBinding
import hu.bp.mrtn.workoutdesigner.interfaces.CurrentClickInterface
import hu.bp.mrtn.workoutdesigner.interfaces.EditExerciseDialogClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutDataViewModel
import kotlin.concurrent.thread


class ExercisesFragment : Fragment(), CurrentClickInterface, EditExerciseDialogClickInterface {


    private val TAG = "ExercisesFragment"
    private var editModeOn = false
    private lateinit var binding: FragmentExercisesBinding
    private lateinit var adapter: ExerciseAdapter
    // private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var db: DatabaseDao
    private val workoutDataViewModel: WorkoutDataViewModel by activityViewModels()





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.db = WorkoutDatabase.getDatabase(requireContext()).getDao()

        this.initMenu()
        this.initRecycleView()
        // this.initTouchHelper()



        binding.btnAddExercise.setOnClickListener {
            this.addExercise(ExerciseModel(workoutID = workoutDataViewModel.workout.workoutID))
            this.binding.rvExercises.scrollToPosition(this.adapter.itemCount - 1)
        }
    }




    override fun onResume() {
        super.onResume()

        this.loadExercises()
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



                        // megjelenítjük a floating buttont
                        // az adapterben újraállítjuk a listenereket

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




    private fun loadExercises() {
        if (workoutDataViewModel.workout.workoutID == null || workoutDataViewModel.workout.workoutID == 0L) {
            Log.i(TAG, "Choose a workout before adding a new exercise")
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




    private fun addExercise(exercise: ExerciseModel) {
        if (exercise.workoutID == null || exercise.workoutID == 0L) {
            Log.i(TAG, "Choose a workout before adding a new exercise")
            return
        }

        thread {
            val id = this.db.insert(exercise)
            exercise.exerciseID = id

            activity?.runOnUiThread {
                this.adapter.addExercise(exercise)
            }
        }
    }




    override fun onWeightClicked(position: Int) {
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




    override fun onRepsClicked(position: Int) {
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




    override fun onRootClicked(position: Int) {
        if (!this.editModeOn) {
            return
        }

        this.showExerciseEditDialog(position)
    }




    override fun onRootLongClicked(position: Int): Boolean {
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




    private fun updateExercise(updatedExercise: ExerciseModel, position: Int) {
        thread {
            this.db.update(updatedExercise)

            activity?.runOnUiThread {
                this.adapter.updateExercise(updatedExercise, position)
            }
        }
    }




    private fun removeExercise(position: Int) {
        thread {
            val exercise = this.adapter.getExerciseAt(position)

            this.db.delete(exercise)

            // todo frissíteni kell az összes többi indexét a sorrendezés miatt

            activity?.runOnUiThread {
                this.adapter.removeExercise(position)
            }
        }
    }




    override fun onSaveExerciseClicked(updatedExercise: ExerciseModel, position: Int) {
        this.updateExercise(updatedExercise, position)
    }




    private fun showExerciseEditDialog(position: Int) {
        val exercise = this.adapter.getExerciseAt(position)
        EditExerciseDialogFragment(this, exercise, position).show(parentFragmentManager, "asd")
    }


}