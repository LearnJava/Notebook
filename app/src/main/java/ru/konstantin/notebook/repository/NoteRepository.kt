package ru.konstantin.notebook.repository

import ru.konstantin.notebook.entity.Note

interface NoteRepository {
    fun getNotes(callback: Callback<List<Note>>)
    fun clear()
    fun add(note: Note, callback: Callback<Note>)
    fun remove(note: Note, callback: Callback<Note>)
    fun edit(note: Note?, callback: Callback<Note?>?): Note?
}