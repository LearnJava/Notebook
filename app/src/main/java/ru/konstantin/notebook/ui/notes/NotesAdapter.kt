package ru.konstantin.notebook.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.notebook.R
import ru.konstantin.notebook.entity.Note
import ru.konstantin.notebook.ui.notes.NotesAdapter.NotesViewHolder
import java.util.*

class NotesAdapter(var fragment: Fragment) : RecyclerView.Adapter<NotesViewHolder>() {
    interface OnNoteClickedListener {
        fun onNoteClickedListener(note: Note)
    }

    interface OnNoteLongClickedListener {
        fun onNoteLongClickedListener(note: Note, index: Int)
    }

    var longClickedListener: OnNoteLongClickedListener? = null
    private val notes = ArrayList<Note>()
    fun setData(toSet: List<Note>?) {
        notes.clear()
        notes.addAll(toSet!!)
    }

    fun add(note: Note): Int {
        notes.add(note)
        return notes.size - 1
    }

    fun update(note: Note) {
        for (i in notes.indices) {
            val item = notes[i]
            if (item.id == note.id) {
                notes.removeAt(i)
                notes.add(i, note)
                return
            }
        }
    }

    fun remove(longClickedNote: Note) {
        notes.remove(longClickedNote)
    }

    var listener: OnNoteClickedListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.noteView.text = note.description
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteView: TextView

        init {
            fragment.registerForContextMenu(itemView)
            itemView.setOnClickListener { v: View? ->
                if (listener != null) {
                    listener!!.onNoteClickedListener(notes[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                itemView.showContextMenu()
                if (longClickedListener != null) {
                    val index = adapterPosition
                    longClickedListener!!.onNoteLongClickedListener(notes[index], index)
                }
                true
            }
            noteView = itemView.findViewById(R.id.note_desc)
        }
    }
}