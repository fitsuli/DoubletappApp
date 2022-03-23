package ru.fitsuli.doubletappapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.fitsuli.doubletappapp.HabitHolder
import ru.fitsuli.doubletappapp.HabitItem
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.Utils.Companion.EDITED_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.EDIT_MODE_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.FRAGMENT_REQUEST_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.HABIT_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.ITEM_ID_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.NEW_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.databinding.FragmentRecyclerBinding

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    companion object {
        private const val ARG_TYPE_NAME = "args_type_name"

        fun newInstance(type: Type) = RecyclerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_TYPE_NAME, type)
            }
        }
    }

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private val listContent = mutableListOf<HabitItem>()
    private var type: Type? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecyclerBinding.bind(view)
        arguments?.let {
            type = it.getParcelable(ARG_TYPE_NAME)
        }

        with(binding) {

            fab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_main_to_add_habit, bundleOf(
                        ITEM_ID_KEY to listContent.size
                    )
                )
            }
            recycler.adapter = object : RecyclerView.Adapter<HabitHolder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                    HabitHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_habit, parent, false)
                    )

                override fun getItemCount(): Int = listContent.size

                override fun onBindViewHolder(holder: HabitHolder, position: Int) {
                    holder.apply {
                        parentCard.setOnClickListener {
                            findNavController().navigate(
                                R.id.action_main_to_add_habit, bundleOf(
                                    EDIT_MODE_KEY to true,
                                    ITEM_ID_KEY to position,
                                    HABIT_ITEM_KEY to listContent[position]
                                )
                            )
                        }
                        listContent[position].srgbColor?.let {
                            parentCard.setCardBackgroundColor(it)
                        }
                        name.text = listContent[position].name
                        description.text = listContent[position].description.also {
                            description.isVisible = it.isNotEmpty()
                        }
                        priority.text = getString(listContent[position].priority.resId)
                        type.text = getString(listContent[position].type.resId)
                        count.text =
                            listContent[position].count.also { count.isVisible = it.isNotEmpty() }

                        period.text = getString(
                            R.string.every_x,
                            listContent[position].period
                                .also { period.isVisible = it.isNotEmpty() })
                    }
                }
            }

            setFragmentResultListener("$FRAGMENT_REQUEST_KEY${type?.name}") { _, bundle ->
                bundle.getParcelable<HabitItem>(NEW_ITEM_KEY)?.let {
                    listContent.add(it)
                    recycler.adapter?.notifyItemChanged(it.id)
                }
                bundle.getParcelable<HabitItem>(EDITED_ITEM_KEY)?.let {
                    listContent[it.id] = it
                    recycler.adapter?.notifyItemChanged(it.id)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}