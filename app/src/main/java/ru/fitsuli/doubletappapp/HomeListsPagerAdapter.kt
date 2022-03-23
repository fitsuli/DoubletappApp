package ru.fitsuli.doubletappapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.fragments.RecyclerFragment

class HomeListsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        0 -> RecyclerFragment.newInstance(Type.GOOD)
        else -> RecyclerFragment.newInstance(Type.BAD)
    }
}