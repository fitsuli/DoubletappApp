package ru.fitsuli.doubletappapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.databinding.FragmentFiltersBinding

class FiltersSheetFragment : Fragment(R.layout.fragment_filters) {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFiltersBinding.bind(view)
    }

}