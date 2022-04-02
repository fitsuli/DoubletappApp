package ru.fitsuli.doubletappapp.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.fitsuli.doubletappapp.HomeListsPagerAdapter
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.Utils
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.databinding.FragmentMainBinding
import java.util.*

// Contains ViewPager's fragments
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        activity?.let { activity ->

            with(binding) {

                viewPager.adapter = HomeListsPagerAdapter(activity)
                viewPager.offscreenPageLimit = 1
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = getString(
                        if (position == 0) Type.GOOD.stringResId else Type.BAD.stringResId
                    )
                }.attach()

                fab.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_main_to_add_habit, bundleOf(
                            Utils.ITEM_ID_KEY to UUID.randomUUID().mostSignificantBits
                        )
                    )
                }
            }
        }
    }
}