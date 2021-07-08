package ru.konstantin.notebook.repository;

import java.util.Date;
import java.util.List;

import ru.konstantin.notebook.entity.Note;

public interface NoteRepository {
    void getNotes(Callback<List<Note>> callback);
    void clear();
    void add(Note note, Callback<Note> callback);

    void remove(Note note, Callback<Note> callback);

    Note edit(Note note, Callback<Note> callback);

}
