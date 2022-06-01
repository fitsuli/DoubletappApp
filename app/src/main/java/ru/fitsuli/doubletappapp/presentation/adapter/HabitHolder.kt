package ru.fitsuli.doubletappapp.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import ru.fitsuli.doubletappapp.R
import ru.fitsuli.doubletappapp.domain.models.HabitDomain

class HabitHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val parentCard: MaterialCardView = view.findViewById(R.id.card_view)
    private val name: TextView = view.findViewById(R.id.name)
    private val description: TextView = view.findViewById(R.id.desc)
    private val priority: TextView = view.findViewById(R.id.priority)
    private val count: TextView = view.findViewById(R.id.count)
    private val period: TextView = view.findViewById(R.id.period)
    private val doneButton: MaterialButton = view.findViewById(R.id.mark_done_button)

    fun onBind(
        context: Context, habit: HabitDomain, onClick: ((id: String) -> Unit)? = null,
        onButtonClick: ((id: String) -> Unit)? = null
    ) {
        if (onClick != null) {
            parentCard.setOnClickListener {
                onClick(habit.id)
            }
        }

        parentCard.setCardBackgroundColor(habit.srgbColor ?: Color.TRANSPARENT)
        name.text = habit.name
        description.text = habit.description.also {
            description.isVisible = it.isNotEmpty()
        }
        priority.text = context.getString(habit.priority.stringResId)
        count.text = context.getString(R.string.times,
            habit.count.also { count.isVisible = it.toString().isNotEmpty() }
        )

        period.text = context.getString(
            R.string.every_x,
            habit.period.toString()
                .also { period.isVisible = it.isNotEmpty() })

        if (onButtonClick != null) {
            doneButton.setOnClickListener {
                onButtonClick(habit.id)
            }
        }
    }
}