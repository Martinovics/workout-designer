package hu.bp.mrtn.workoutdesigner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutModel
import hu.bp.mrtn.workoutdesigner.data.WorkoutWithExercises
import hu.bp.mrtn.workoutdesigner.databinding.ExerciseRowBinding
import hu.bp.mrtn.workoutdesigner.interfaces.CurrentClickInterface
import hu.bp.mrtn.workoutdesigner.interfaces.ItemClickInterface
import java.util.Collections
import kotlin.math.max
import kotlin.math.min




class ExerciseAdapter(private val listener: CurrentClickInterface): RecyclerView.Adapter<ExerciseAdapter.ItemViewHolder>() {



    private var exercises = ArrayList<ExerciseModel>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        ExerciseRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )




    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val exercise = this.exercises[position]

        holder.binding.tvExerciseName.text = exercise.exerciseName
        holder.binding.tvExerciseDescription.text = exercise.exerciseDescription
        holder.binding.tvExerciseComment.text = exercise.exerciseComment
        holder.binding.tvExerciseCurrWeight.text = exercise.currWeight.toString()
        holder.binding.tvExerciseGoalWeight.text = exercise.goalWeight.toString()
        holder.binding.tvExerciseCurrReps.text = exercise.currReps.toString()
        holder.binding.tvExerciseGoalReps.text = exercise.goalReps.toString()

        holder.binding.root.setOnClickListener { listener.onRootClicked(position) }
        holder.binding.root.setOnLongClickListener { listener.onRootLongClicked(position) }
        holder.binding.llExerciseWeight.setOnClickListener { listener.onWeightClicked(position) }
        holder.binding.llExerciseReps.setOnClickListener { listener.onRepsClicked(position) }
    }




    override fun getItemCount() = this.exercises.size




    fun getExerciseAt(position: Int): ExerciseModel {
        return this.exercises[position]
    }




    fun clearExercises() {
        this.exercises.clear()
        notifyDataSetChanged()
    }




    fun addExercise(exercise: ExerciseModel) {
        this.exercises.add(exercise)
        notifyItemInserted(itemCount - 1)
    }




    fun removeExercise(position: Int) {
        this.exercises.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }




    fun swapExercises(fromPosition: Int, toPosition: Int) {
        Collections.swap(this.exercises, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)

        if (fromPosition < toPosition) {
            notifyItemRangeChanged(fromPosition, toPosition - fromPosition + 1)
        } else {
            // from=1 to=0 esetén csak úgy beugrik a helyére --> nem jó, de nem is tragikus
            notifyItemRangeChanged(toPosition, fromPosition - toPosition + 1)
        }

    }




    fun setCurrentReps(currReps: Int, position: Int) {
        this.exercises[position].currReps = currReps
        notifyItemChanged(position)
    }




    fun setCurrentWeight(currWeight: Int, position: Int) {
        this.exercises[position].currWeight = currWeight
        notifyItemChanged(position)
    }




    fun updateExercise(updatedExercise: ExerciseModel, position: Int) {
        this.exercises[position] = updatedExercise
        notifyItemChanged(position)
    }




    inner class ItemViewHolder(val binding: ExerciseRowBinding) : RecyclerView.ViewHolder(binding.root)

}