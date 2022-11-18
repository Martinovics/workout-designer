package hu.bp.mrtn.workoutdesigner.adapters

import android.util.Log
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




    fun getWorkoutAt(position: Int): WorkoutModel {
        return this.workouts[position]
    }



    fun addWorkout(workout: WorkoutModel) {
        this.workouts.add(workout)
        notifyItemInserted(itemCount - 1)
    }




    fun removeWorkout(position: Int) {
        this.workouts.removeAt(position)
        notifyItemRemoved(position)  // ez valójában nem frissíti le az elemek új pozícióját, ami a törlés miatt lett
        notifyItemRangeChanged(position, itemCount)  // kell, különben nem frissül az elemek pozíciója
    }




    fun swapWorkouts(fromPosition: Int, toPosition: Int) {
        Collections.swap(this.workouts, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }




    fun setWorkoutName(workout_name: String, position: Int) {
        this.workouts[position].workoutName = workout_name
        notifyItemChanged(position)
    }




    fun setWorkoutDescription(workout_description: String, position: Int) {
        this.workouts[position].workoutDescription = workout_description
        notifyItemChanged(position)
    }


    inner class ItemViewHolder(val binding: WorkoutRowBinding) : RecyclerView.ViewHolder(binding.root)

}