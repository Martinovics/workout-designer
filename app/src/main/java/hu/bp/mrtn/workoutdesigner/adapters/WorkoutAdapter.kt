package hu.bp.mrtn.workoutdesigner.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutWithExercises
import hu.bp.mrtn.workoutdesigner.databinding.WorkoutRowBinding
import hu.bp.mrtn.workoutdesigner.interfaces.ItemClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel
import java.util.Collections
import kotlin.math.max
import kotlin.math.min


class WorkoutAdapter(private val listener: ItemClickInterface): RecyclerView.Adapter<WorkoutAdapter.ItemViewHolder>() {


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

        holder.binding.root.setOnClickListener { listener.onItemClicked(position) }
        holder.binding.root.setOnLongClickListener { listener.onItemLongClicked(position) }
    }




    override fun getItemCount() = this.workouts.size




    fun getWorkoutAt(position: Int): WorkoutModel {
        return this.workouts[position].workout
    }




    fun addWorkout(workout: WorkoutModel) {
        this.workouts.add(WorkoutPreviewModel(workout, 0, 0))
        notifyItemInserted(itemCount - 1)
    }




    fun addWorkout(workout: WorkoutModel, totalSeries: Int, totalSets: Int) {
        this.workouts.add(WorkoutPreviewModel(workout, totalSeries, totalSets))
        notifyItemInserted(itemCount - 1)
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




    fun setWorkoutName(workoutName: String, position: Int) {
        this.workouts[position].workout.workoutName = workoutName
        notifyItemChanged(position)
    }


    fun setWorkoutDescription(workoutDescription: String, position: Int) {
        this.workouts[position].workout.workoutDescription = workoutDescription
        notifyItemChanged(position)
    }


    fun setWorkoutColorHex(colorHex: String, position: Int) {
        this.workouts[position].workout.workoutColorHex = colorHex
        notifyItemChanged(position)
    }


    fun setTotalSeries(totalSeries: Int, position: Int) {
        this.workouts[position].totalSeries = totalSeries
        notifyItemChanged(position)
    }


    fun setTotalSets(totalSets: Int, position: Int) {
        this.workouts[position].totalSets = totalSets
        notifyItemChanged(position)
    }



    fun updateWorkout(updatedWorkout: WorkoutModel, position: Int) {
        this.workouts[position].workout = updatedWorkout
        notifyItemChanged(position)
    }




    inner class ItemViewHolder(val binding: WorkoutRowBinding) : RecyclerView.ViewHolder(binding.root)

}