package hu.bp.mrtn.workoutdesigner.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bp.mrtn.workoutdesigner.fragments.ExercisesFragment
import hu.bp.mrtn.workoutdesigner.fragments.ProgressFragment
import hu.bp.mrtn.workoutdesigner.fragments.WorkoutsFragment


class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {


    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { ProgressFragment() }
            1 -> { WorkoutsFragment() }
            2 -> { ExercisesFragment() }
            else -> { throw Resources.NotFoundException("Couldn't find fragment at $position") }
        }
    }


}