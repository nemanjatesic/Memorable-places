package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tabs.*
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.viewpager.PagerTopAdapter

class TabsFragment : Fragment(R.layout.fragment_tabs) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initTabs()
    }

    private fun initTabs() {
        viewPagerTop.adapter = PagerTopAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPagerTop)
    }

}