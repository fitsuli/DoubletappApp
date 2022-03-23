package ru.fitsuli.doubletappapp

import android.view.View
import android.widget.TextView
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
}