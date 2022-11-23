package hu.bp.mrtn.workoutdesigner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.databinding.WorkoutRowBinding
import hu.bp.mrtn.workoutdesigner.interfaces.WorkoutClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel
import java.util.Collections


class WorkoutAdapter(private val listener: WorkoutClickInterface): RecyclerView.Adapter<WorkoutAdapter.ItemViewHolder>() {


    private var workouts = ArrayList<WorkoutPreviewModel>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        WorkoutRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )




    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val workout = this.workouts[position]

        val totalSeries = workout.totalSeries  // todo ki kell számolni -> ahány különboző elem van
        val totalSets = workout.totalSets

        holder.binding.tvWorkoutName.text = workout.workout.workoutName
        holder.binding.tvWorkoutSeries.text = totalSeries.toString()
        holder.binding.tvWorkoutSets.text = totalSets.toString()
        holder.binding.tvWorkoutDescription.text = workout.workout.workoutDescription

        holder.binding.root.setOnClickListener { listener.onWorkoutAnywhereClicked(position) }
        holder.binding.root.setOnLongClickListener { listener.onWorkoutAnywhereLongClicked(position) }
    }




    override fun getItemCount() = this.workouts.size




    fun getWorkoutAt(position: Int): WorkoutModel {
        return this.workouts[position].workout
    }




    fun addWorkout(workout: WorkoutModel): Int {
        this.workouts.add(WorkoutPreviewModel(workout, 0, 0))
        notifyItemInserted(itemCount - 1)
        return itemCount - 1
    }




    fun addWorkout(workout: WorkoutModel, totalSeries: Int, totalSets: Int) {
        this.workouts.add(WorkoutPreviewModel(workout, totalSeries, totalSets))
        notifyItemInserted(itemCount - 1)
    }




    fun updateWorkout(updatedWorkout: WorkoutModel, position: Int) {
        this.workouts[position].workout = updatedWorkout
        notifyItemChanged(position)
    }




    fun removeWorkout(position: Int) {
        this.workouts.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }




    fun swapWorkouts(fromPosition: Int, toPosition: Int) {
        Collections.swap(this.workouts, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)

        if (fromPosition < toPosition) {
            notifyItemRangeChanged(fromPosition, toPosition - fromPosition + 1)
        } else {
            // from=1 to=0 esetén csak úgy beugrik a helyére --> nem jó, de nem is tragikus
            notifyItemRangeChanged(toPosition, fromPosition - toPosition + 1)
        }

    }




    private fun getWorkoutNames(): ArrayList<String> {
        val workoutNames = ArrayList<String>()
        for (workout in this.workouts) {
            workoutNames.add(workout.workout.workoutName)
        }
        return workoutNames
    }




    fun genUniqueWorkoutName(): String {

        val workoutNames = this.getWorkoutNames()

        for (i in 1..1000) {
            val uniqueName = "Workout-$i"
            if (!workoutNames.contains(uniqueName)) {
                return uniqueName
            }
        }
        return ""
    }




    fun conflictingWorkoutName(workoutName: String): Boolean {
        val workoutNames = this.getWorkoutNames()
        return workoutNames.contains(workoutName)
    }




    fun updateTotalSeriesAndReps(totalSeries: Int, totalSets: Int, position: Int) {
        this.workouts[position].totalSeries = totalSeries
        this.workouts[position].totalSets = totalSets
        notifyItemChanged(position)
    }




    inner class ItemViewHolder(val binding: WorkoutRowBinding) : RecyclerView.ViewHolder(binding.root)

}