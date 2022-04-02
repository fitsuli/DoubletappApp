package ru.fitsuli.doubletappapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HabitRecyclerViewAdapter(
    private val context: Context,
    var listContent: List<HabitItem> = listOf(),
    private val onCardClick: ((position: Int, item: HabitItem) -> Unit)? = null
) : RecyclerView.Adapter<HabitHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_habit, parent, false)
        )

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        holder.onBind(context, listContent[position],
            onClick = { onCardClick?.invoke(position, listContent[position]) }
        )
    }

    override fun getItemCount(): Int = listContent.size
}