package ru.konstantin.notebook.ui.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ru.konstantin.notebook.R
import ru.konstantin.notebook.entity.Note
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class NoteDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteId = view.findViewById<TextView>(R.id.id)
        val noteDesc = view.findViewById<TextView>(R.id.description)
        val noteText = view.findViewById<TextView>(R.id.note_text)
        val noteDate = view.findViewById<TextView>(R.id.note_date)
        var note: Note? = null
        if (arguments != null) {
            note = arguments?.getParcelable(ARG_NOTE)?:Note()
            noteId.text = note!!.id.toString()
            noteDesc.text = note.description
            noteText.text = note.noteText
            noteDate.text = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(note.noteDate),
                ZoneId.systemDefault()
            ).toString()
        }
    }

    companion object {
        const val ARG_NOTE = "ARG_NOTE"
        @JvmStatic
        fun newInstance(note: Note?): NoteDetailsFragment {
            val noteDetailsFragment = NoteDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_NOTE, note)
            noteDetailsFragment.arguments = bundle
            return noteDetailsFragment
        }
    }
}