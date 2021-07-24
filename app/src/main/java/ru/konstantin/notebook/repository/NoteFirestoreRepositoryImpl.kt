package ru.konstantin.notebook.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import ru.konstantin.notebook.entity.Note
import java.util.*

class NoteFirestoreRepositoryImpl : NoteRepository {
    var firebaseFirestore = FirebaseFirestore.getInstance()
    override fun getNotes(callback: Callback<List<Note>>) {
        firebaseFirestore.collection(NOTES)
            .orderBy(NOTE_DATE, Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = ArrayList<Note>()
                    val tasks = task.result ?: return@OnCompleteListener
                    val docs = tasks.documents
                    for (document in docs) {
                        val noteText = document[NOTE_TEXT] as String?
                        val desc = document[DESC] as String?
                        val longDate = document[NOTE_DATE].toString().toLong()
                        val noteDate = Date(longDate)
                        result.add(Note(document.id, desc, noteText, noteDate.time))
                    }
                    callback.onSuccess(result)
                } else {
                    task.exception
                }
            })
    }

    override fun add(note: Note, callback: Callback<Note>) {
        val data = HashMap<String, Any?>()
        data[DESC] = note.description
        data[NOTE_TEXT] = note.noteText
        data[NOTE_DATE] = note.noteDate
        firebaseFirestore.collection(NOTES)
            .add(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    note.id = task.result!!.id
                    callback.onSuccess(note)
                }
            }
    }

    override fun clear() {}
    override fun remove(note: Note, callback: Callback<Note>) {
        firebaseFirestore.collection(NOTES)
            .document(note.id!!)
            .delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(note)
                }
            }
    }

    override fun edit(note: Note?, callback: Callback<Note?>?): Note? {
        return null
    }

    companion object {
        const val NOTES = "notes"
        const val NOTE_DATE = "noteDate"
        const val DESC = "description"
        const val NOTE_TEXT = "noteText"
    }
}