package hu.bp.mrtn.workoutdesigner.interfaces

interface ItemClickInterface {
    fun onItemClicked(position: Int)
    fun onItemLongClicked(position: Int): Boolean
}