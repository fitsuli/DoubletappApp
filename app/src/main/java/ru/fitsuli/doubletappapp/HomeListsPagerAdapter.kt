package ru.fitsuli.doubletappapp

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.fragments.RecyclerFragment

class HomeListsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = mutableListOf(
        RecyclerFragment.newInstance(Type.GOOD),
        RecyclerFragment.newInstance(Type.BAD)
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}