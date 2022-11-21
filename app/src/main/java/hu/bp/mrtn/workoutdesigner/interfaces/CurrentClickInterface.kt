package hu.bp.mrtn.workoutdesigner.interfaces

interface CurrentClickInterface {
    fun onWeightClicked(position: Int)
    fun onRepsClicked(position: Int)
    fun onRootClicked(position: Int)
    fun onRootLongClicked(position: Int): Boolean
}