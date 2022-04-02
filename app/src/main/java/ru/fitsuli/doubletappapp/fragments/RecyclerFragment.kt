package ru.fitsuli.doubletappapp.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.fitsuli.doubletappapp.HabitRecyclerViewAdapter
import ru.fitsuli.doubletappapp.HabitViewModel
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.Utils.Companion.EDIT_MODE_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.HABIT_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.ITEM_ID_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.databinding.FragmentRecyclerBinding

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    companion object {
        private const val ARG_TYPE_NAME = "args_type_name"

        fun newInstance(type: Type = Type.GOOD) = RecyclerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_TYPE_NAME, type)
            }
        }
    }

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private var type = Type.GOOD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]
        _binding = FragmentRecyclerBinding.bind(view)

        arguments?.let { bundle ->
            bundle.getParcelable<Type>(ARG_TYPE_NAME)?.let { type ->
                this.type = type
            }
        }

        val adapter = HabitRecyclerViewAdapter(
            requireContext(),
            onCardClick = { item ->
                findNavController().navigate(
                    R.id.action_main_to_add_habit, bundleOf(
                        EDIT_MODE_KEY to true,
                        ITEM_ID_KEY to item.id,
                        HABIT_ITEM_KEY to item
                    )
                )
            }
        )
        binding.recycler.adapter = adapter
        viewModel.listContent.observe(viewLifecycleOwner) {
            adapter.submitList(viewModel.getFilteredList(type)) // TODO: is it okay not to use a given list?
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}