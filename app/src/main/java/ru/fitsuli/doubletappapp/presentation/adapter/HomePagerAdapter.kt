package ru.fitsuli.doubletappapp.presentation.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.presentation.fragments.RecyclerFragment

class HomePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = mutableListOf(
        RecyclerFragment.newInstance(Type.GOOD),
        RecyclerFragment.newInstance(Type.BAD)
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}