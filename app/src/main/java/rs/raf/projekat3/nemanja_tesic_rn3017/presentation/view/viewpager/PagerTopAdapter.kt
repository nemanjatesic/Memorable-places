package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments.ListFragment
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments.ViewPlacesFragment

class PagerTopAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_MAP = 0
        const val FRAGMENT_LIST = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_MAP -> ViewPlacesFragment()
            else -> ListFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_MAP -> "Mapa"
            else -> "Lista"
        }
    }

}