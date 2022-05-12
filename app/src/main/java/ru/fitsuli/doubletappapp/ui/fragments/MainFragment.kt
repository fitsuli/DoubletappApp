package ru.fitsuli.doubletappapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.Utils
import ru.fitsuli.doubletappapp.databinding.FragmentMainBinding
import ru.fitsuli.doubletappapp.ui.adapter.HomePagerAdapter
import ru.fitsuli.doubletappapp.ui.viewmodels.ListViewModel
import java.util.*

// Contains ViewPager's fragments
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        val viewModel = ViewModelProvider(requireActivity())[ListViewModel::class.java]

        with(binding) {

            viewPager.adapter = HomePagerAdapter(requireActivity())
            viewPager.offscreenPageLimit = 1
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(
                    if (position == 0) Type.GOOD.stringResId else Type.BAD.stringResId
                )
            }.attach()
            fab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_main_to_add_habit, bundleOf(
                        Utils.ITEM_ID_KEY to UUID.randomUUID().mostSignificantBits.toString()
                    )
                )
            }

            filters.searchEditText.doAfterTextChanged {
                viewModel.setFilterName(it.toString())
            }
            filters.byAscendingIcon.setOnClickListener {
                viewModel.setSorting(SortBy.ASCENDING)
            }
            filters.byDefaultIcon.setOnClickListener {
                viewModel.setSorting(SortBy.NONE)
            }
            filters.byDescendingIcon.setOnClickListener {
                viewModel.setSorting(SortBy.DESCENDING)
            }

        }
    }
}