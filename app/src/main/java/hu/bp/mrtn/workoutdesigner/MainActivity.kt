package hu.bp.mrtn.workoutdesigner

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hu.bp.mrtn.workoutdesigner.adapters.ViewPagerAdapter
import hu.bp.mrtn.workoutdesigner.databinding.ActivityMainBinding
import hu.bp.mrtn.workoutdesigner.databinding.ToolbarBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.root)

        // page viewer setup
        this.binding.viewPager.adapter = ViewPagerAdapter(this)
        this.binding.viewPager.setCurrentItem(1, false)

        TabLayoutMediator(this.binding.tabs, this.binding.viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> { "Progress" }
                1 -> { "Workouts" }
                2 -> { "Exercises" }
                else -> { throw Resources.NotFoundException("Couldn't find tab title at $position") }
            }
        }.attach()

    }
}