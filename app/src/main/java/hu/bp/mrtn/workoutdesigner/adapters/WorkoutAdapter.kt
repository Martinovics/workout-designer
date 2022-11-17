package hu.bp.mrtn.workoutdesigner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.databinding.WorkoutRowBinding
import hu.bp.mrtn.workoutdesigner.interfaces.ItemClickInterface
import hu.bp.mrtn.workoutdesigner.models.WorkoutPreviewModel




class WorkoutAdapter(private val listener: ItemClickInterface): RecyclerView.Adapter<WorkoutAdapter.ItemViewHolder>() {


    private var workouts = ArrayList<WorkoutPreviewModel>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        WorkoutRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )




    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.textView2.text = "asd"
    }




    override fun getItemCount() = this.workouts.size




    fun addWorkout(workout: WorkoutPreviewModel) {
        this.workouts.add(workout)
        notifyItemInserted(this.workouts.size - 1)
    }




    fun removeWorkout(position: Int) {
        this.workouts.removeAt(position)
        notifyItemRemoved(position)
    }




    inner class ItemViewHolder(val binding: WorkoutRowBinding) : RecyclerView.ViewHolder(binding.root)

}