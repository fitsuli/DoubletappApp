package ru.fitsuli.doubletappapp

import android.os.Parcel
import android.os.Parcelable

data class HabitItem(
    val name: String, val description: String, val priorityPosition: Int,
    val type: Int, val period: String, val count: String,
    val id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(priorityPosition)
        parcel.writeInt(type)
        parcel.writeString(period)
        parcel.writeString(count)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<HabitItem> {
        override fun createFromParcel(parcel: Parcel) = HabitItem(parcel)

        override fun newArray(size: Int): Array<HabitItem?> = arrayOfNulls(size)
    }

}
