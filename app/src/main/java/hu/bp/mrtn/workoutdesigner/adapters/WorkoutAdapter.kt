package hu.bp.mrtn.workoutdesigner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.databinding.WorkoutRowBinding
import hu.bp.mrtn.workoutdesigner.interfaces.ItemClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutModel
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel
import java.util.Collections


class WorkoutAdapter(private val listener: ItemClickInterface): RecyclerView.Adapter<WorkoutAdapter.ItemViewHolder>() {


    private var workouts = ArrayList<WorkoutModel>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        WorkoutRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )




    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val workout = this.workouts[position]

        holder.binding.tvWorkoutName.text = workout.workoutName
        holder.binding.tvWorkoutSeries.text = workout.totalSeries.toString()
        holder.binding.tvWorkoutSets.text = workout.totalSets.toString()
        holder.binding.tvWorkoutDescription.text = workout.workoutDescription

        holder.binding.root.setOnClickListener { listener.onItemClicked(position) }
        holder.binding.root.setOnLongClickListener { listener.onItemLongClicked(position) }
    }




    override fun getItemCount() = this.workouts.size




    fun addWorkout(workout: WorkoutModel) {
        this.workouts.add(workout)
        notifyItemInserted(this.workouts.size - 1)
    }




    fun removeWorkout(position: Int) {
        this.workouts.removeAt(position)
        notifyItemRemoved(position)
    }




    fun swapWorkouts(fromPosition: Int, toPosition: Int) {
        Collections.swap(this.workouts, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }




    inner class ItemViewHolder(val binding: WorkoutRowBinding) : RecyclerView.ViewHolder(binding.root)

}