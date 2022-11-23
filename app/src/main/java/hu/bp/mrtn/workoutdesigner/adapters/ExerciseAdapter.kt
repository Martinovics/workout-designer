package hu.bp.mrtn.workoutdesigner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bp.mrtn.workoutdesigner.data.ExerciseModel
import hu.bp.mrtn.workoutdesigner.databinding.ExerciseRowBinding
import hu.bp.mrtn.workoutdesigner.interfaces.ExerciseClickInterface
import java.util.Collections


class ExerciseAdapter(private val listener: ExerciseClickInterface): RecyclerView.Adapter<ExerciseAdapter.ItemViewHolder>() {


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

        holder.binding.root.setOnClickListener { listener.onExerciseAnywhereClicked(position) }
        holder.binding.root.setOnLongClickListener { listener.onExerciseAnywhereLongClicked(position) }
        holder.binding.llExerciseWeight.setOnClickListener { listener.onExerciseWeightBoxClicked(position) }
        holder.binding.llExerciseReps.setOnClickListener { listener.onExerciseRepsBoxClicked(position) }
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



    fun updateExercise(updatedExercise: ExerciseModel, position: Int) {
        this.exercises[position] = updatedExercise
        notifyItemChanged(position)
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




    inner class ItemViewHolder(val binding: ExerciseRowBinding) : RecyclerView.ViewHolder(binding.root)

}