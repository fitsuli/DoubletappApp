package ru.fitsuli.doubletappapp.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.text.format.DateUtils
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
    private val goal: TextView = view.findViewById(R.id.goal)
    private val lastTimeDid: TextView = view.findViewById(R.id.last_time_did)
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
        goal.text = context.getString(
            R.string.goal_n_times,
            context.getString(R.string.n_times, habit.doneDates.size),
            context.getString(R.string.n_times, habit.goalCount)
        )

        lastTimeDid.text = context.getString(
            R.string.did_last_time,
            if (habit.doneDates.isEmpty()) context.getString(R.string.never)
            else DateUtils.getRelativeTimeSpanString(
                context,
                habit.doneDates.last().toInstant().toEpochMilli()
            )
        )

        if (onButtonClick != null) {
            doneButton.setOnClickListener {
                onButtonClick(habit.id)
            }
        }
    }
}