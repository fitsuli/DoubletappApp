package ru.fitsuli.doubletappapp

import androidx.recyclerview.widget.DiffUtil

class HabitCallback(val oldItem: HabitItem, val newItem: HabitItem) :
    DiffUtil.ItemCallback<HabitItem>() {

    override fun areItemsTheSame(oldItem: HabitItem, newItem: HabitItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HabitItem, newItem: HabitItem) =
        oldItem == newItem
}