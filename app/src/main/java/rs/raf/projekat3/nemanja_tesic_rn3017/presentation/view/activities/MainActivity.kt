package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.viewpager.PagerBottomAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initViewPager()
        initNavigation()
    }

    private fun initViewPager() {
        viewPager.adapter = PagerBottomAdapter(supportFragmentManager)
    }

    private fun initNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_add_place ->  {
                    viewPager.setCurrentItem(PagerBottomAdapter.FRAGMENT_ADD_PLACE, false)
                }
                R.id.navigation_places ->  {
                    viewPager.setCurrentItem(PagerBottomAdapter.FRAGMENT_TABS, false)
                }
            }
            true
        }
    }
}