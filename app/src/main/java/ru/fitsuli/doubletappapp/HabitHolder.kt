package ru.fitsuli.doubletappapp

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class HabitHolder(view: View) : RecyclerView.ViewHolder(view) {
    val parentCard: MaterialCardView = view.findViewById(R.id.card_view)
    val name: TextView = view.findViewById(R.id.name)
    val description: TextView = view.findViewById(R.id.desc)
    val priority: TextView = view.findViewById(R.id.priority)
    val type: TextView = view.findViewById(R.id.type)
    val count: TextView = view.findViewById(R.id.count)
    val period: TextView = view.findViewById(R.id.period)

    fun onBind(context: Context, habitItem: HabitItem, onCardClick: ((View) -> Unit)? = null) {
        onCardClick?.let { parentCard.setOnClickListener(it) }

        habitItem.srgbColor?.let {
            parentCard.setCardBackgroundColor(it)
        }
        name.text = habitItem.name
        description.text = habitItem.description.also {
            description.isVisible = it.isNotEmpty()
        }
        priority.text = context.getString(habitItem.priority.resId)
        type.text = context.getString(habitItem.type.resId)
        count.text =
            habitItem.count.also { count.isVisible = it.isNotEmpty() }

        period.text = context.getString(
            R.string.every_x,
            habitItem.period
                .also { period.isVisible = it.isNotEmpty() })
    }
}