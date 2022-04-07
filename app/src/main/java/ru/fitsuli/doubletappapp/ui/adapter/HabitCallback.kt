package ru.fitsuli.doubletappapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitCallback : DiffUtil.ItemCallback<HabitItem>() {

    override fun areItemsTheSame(oldItem: HabitItem, newItem: HabitItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HabitItem, newItem: HabitItem) =
        oldItem == newItem
}