package ru.fitsuli.doubletappapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.fitsuli.doubletappapp.domain.models.HabitDomain

class HabitCallback : DiffUtil.ItemCallback<HabitDomain>() {

    override fun areItemsTheSame(oldItem: HabitDomain, newItem: HabitDomain) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HabitDomain, newItem: HabitDomain) =
        oldItem == newItem
}