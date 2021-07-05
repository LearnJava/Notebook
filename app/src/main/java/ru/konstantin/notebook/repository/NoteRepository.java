package ru.konstantin.notebook.repository;

import java.util.Date;
import java.util.List;

import ru.konstantin.notebook.entity.Note;

public interface NoteRepository {
    List<Note> getNotes();
    void clear();
    Note add(String desc, String noteText);
    void remove(Note note);
    Note edit(Note note, String desc, String noteText, Date date);
}
