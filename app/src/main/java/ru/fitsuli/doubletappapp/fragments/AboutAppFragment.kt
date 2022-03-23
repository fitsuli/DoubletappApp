package ru.fitsuli.doubletappapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.databinding.FragmentAboutAppBinding
import ru.fitsuli.doubletappapp.openLink

class AboutAppFragment : Fragment(R.layout.fragment_about_app) {

    private var _binding: FragmentAboutAppBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAboutAppBinding.bind(view)

        val ctx = requireContext()
        with(binding) {
            sourceButton.setOnClickListener { ctx.openLink("https://github.com/fitsuli/DoubletappApp") }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}