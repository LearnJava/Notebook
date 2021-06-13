package ru.konstantin.notebook.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.konstantin.notebook.entity.Note;

public class NoteRepositoryImpl implements NoteRepository {

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> noteArrayList = new ArrayList<>();
        noteArrayList.add(new Note(1, "Заметка для Алёны", "Заметка 1", new Date()));
        noteArrayList.add(new Note(2, "Заметка для Лены", "Заметка 2", new Date()));
        noteArrayList.add(new Note(3, "Заметка для Димы", "Заметка 3", new Date()));
        noteArrayList.add(new Note(4, "Заметка для Кости", "Заметка 4", new Date()));
        noteArrayList.add(new Note(5, "Заметка для Тони", "Заметка 5", new Date()));
        return noteArrayList;
    }
}
