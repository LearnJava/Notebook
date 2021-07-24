package ru.konstantin.notebook.entity

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

class Note : Parcelable {
    constructor() {}
    constructor(id: String?, description: String?, note: String?, noteDate: Long) {
        this.id = id
        this.description = description
        noteText = note
        this.noteDate = noteDate
    }

    var id: String? = null
    var description: String? = null
    var noteText: String? = null
    var noteDate: Long = 0

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        description = `in`.readString()
        noteText = `in`.readString()
        noteDate = `in`.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(description)
        dest.writeString(noteText)
        dest.writeLong(noteDate)
    }

    companion object {
        @JvmField val CREATOR: Creator<Note?> = object : Creator<Note?> {
            override fun createFromParcel(`in`: Parcel): Note? {
                return Note(`in`)
            }

            override fun newArray(size: Int): Array<Note?> {
                return arrayOfNulls(size)
            }
        }
    }
}