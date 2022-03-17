package ru.fitsuli.doubletappapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.fitsuli.doubletappapp.HabitItem
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.Utils.Companion.EDITED_ITEM_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.FRAGMENT_REQUEST_KEY
import ru.fitsuli.doubletappapp.Utils.Companion.NEW_ITEM_KEY
import ru.fitsuli.doubletappapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val listContent = mutableListOf<HabitItem>()

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var parentCard: MaterialCardView = view.findViewById(R.id.card_view)
        var name: TextView = view.findViewById(R.id.name)
        var description: TextView = view.findViewById(R.id.desc)
        var priority: TextView = view.findViewById(R.id.priority)
        var type: TextView = view.findViewById(R.id.type)
        var count: TextView = view.findViewById(R.id.count)
        var period: TextView = view.findViewById(R.id.period)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        with(binding) {

            fab.setOnClickListener {
                findNavController().navigate(R.id.action_main_to_add_habit)
            }
            recycler.adapter = object : RecyclerView.Adapter<ItemHolder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                    ItemHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_habit, parent, false)
                    )

                override fun getItemCount(): Int = listContent.size

                override fun onBindViewHolder(holder: ItemHolder, position: Int) {
                    holder.apply {
                        parentCard.setOnClickListener {
                            findNavController().navigate(R.id.action_main_to_add_habit)
/*                            startActivity(
                                Intent(this@MainActivity, AddHabitActivity::class.java).apply {
                                    putExtra("edit_mode", true)
                                    putExtra("item_id", position)
                                    putExtra("habit_data", listContent[position])
                                }
                            )*/
                        }
                        listContent[position].srgbColor?.let {
                            parentCard.setCardBackgroundColor(it)
                        }
                        name.text = listContent[position].name
                        description.text = listContent[position].description.also {
                            description.isVisible = it.isNotEmpty()
                        }
                        priority.text = getString(listContent[position].priorityPosition.resId)
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

            setFragmentResultListener(FRAGMENT_REQUEST_KEY) { _, bundle ->
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