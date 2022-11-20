package hu.bp.mrtn.workoutdesigner.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
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

        holder.binding.tvWorkoutName.text = workout.workoutName
        holder.binding.tvWorkoutSeries.text = workout.totalSeries.toString()
        holder.binding.tvWorkoutSets.text = workout.totalSets.toString()
        holder.binding.tvWorkoutDescription.text = workout.workoutDescription

        holder.binding.root.setOnClickListener { listener.onItemClicked(position) }
        holder.binding.root.setOnLongClickListener { listener.onItemLongClicked(position) }
    }




    override fun getItemCount() = this.workouts.size




    fun getWorkoutAt(position: Int): WorkoutPreviewModel {
        return this.workouts[position]
    }



    fun addWorkout(workout: WorkoutPreviewModel) {
        this.workouts.add(workout)
        notifyItemInserted(itemCount - 1)
    }




    fun removeWorkout(position: Int) {
        this.workouts.removeAt(position)
        Log.d("adapter", "removed at=$position")
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }




    fun swapWorkouts(fromPosition: Int, toPosition: Int) {
        Collections.swap(this.workouts, fromPosition, toPosition)
        Log.d("adapter", "moved from=$fromPosition to=$toPosition")
        notifyItemMoved(fromPosition, toPosition)

        if (fromPosition < toPosition) {
            notifyItemRangeChanged(fromPosition, toPosition - fromPosition + 1)
        } else {
            // from=1 to=0 esetén csak úgy beugrik a helyére --> nem jó, de nem is tragikus
            notifyItemRangeChanged(toPosition, fromPosition - toPosition + 1)
        }

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