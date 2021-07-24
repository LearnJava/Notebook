package ru.konstantin.notebook.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.konstantin.notebook.R
import ru.konstantin.notebook.entity.Note

class NoteDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        if (savedInstanceState == null) {
            val note: Note = intent?.getParcelableExtra(ARG_NOTE)?:Note()
            supportFragmentManager
                .beginTransaction() //                    .replace(R.id.container, NoteDetailsFragment.newInstance(note))
                .replace(R.id.container, EditNoteFragment.newInstance(note))
                .commit()
        }
    }

    companion object {
        const val ARG_NOTE = "ARG_NOTE"
    }
}