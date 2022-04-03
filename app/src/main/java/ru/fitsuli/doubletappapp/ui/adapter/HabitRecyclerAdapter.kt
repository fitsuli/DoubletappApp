package ru.fitsuli.doubletappapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitRecyclerAdapter(
    private val context: Context,
    private val onCardClick: ((item: HabitItem) -> Unit)? = null
) : ListAdapter<HabitItem, HabitHolder>(HabitCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_habit, parent, false)
        )

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        holder.onBind(context, getItem(position),
            onClick = { onCardClick?.invoke(getItem(position)) }
        )
    }
}