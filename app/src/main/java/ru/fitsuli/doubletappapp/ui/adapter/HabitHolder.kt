package ru.fitsuli.doubletappapp.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val parentCard: MaterialCardView = view.findViewById(R.id.card_view)
    private val name: TextView = view.findViewById(R.id.name)
    private val description: TextView = view.findViewById(R.id.desc)
    private val priority: TextView = view.findViewById(R.id.priority)
    private val count: TextView = view.findViewById(R.id.count)
    private val period: TextView = view.findViewById(R.id.period)

    fun onBind(context: Context, habit: HabitItem, onClick: (View) -> Unit) {
        parentCard.setOnClickListener(onClick)

        habit.srgbColor?.let {
            parentCard.setCardBackgroundColor(it)
        }
        name.text = habit.name
        description.text = habit.description.also {
            description.isVisible = it.isNotEmpty()
        }
        priority.text = context.getString(habit.priority.stringResId)
        count.text =
            habit.count.also { count.isVisible = it.isNotEmpty() }

        period.text = context.getString(
            R.string.every_x,
            habit.period
                .also { period.isVisible = it.isNotEmpty() })
    }
}