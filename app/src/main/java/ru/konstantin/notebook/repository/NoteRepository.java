package ru.konstantin.notebook.repository;

import java.util.List;

import ru.konstantin.notebook.entity.Note;

public interface NoteRepository {
    List<Note> getNotes();
}
