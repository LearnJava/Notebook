package ru.konstantin.notebook.repository;

import java.util.List;

import ru.konstantin.notebook.entity.Note;

public interface NoteRepository {

    void getNotes(Callback<List<Note>> callback);

    Note add(Note note);

    void clear();

    void remove(Note note);

    Note edit(Note note);
}
