package ru.konstantin.notebook.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.konstantin.notebook.entity.Note;

public class NoteRepositoryImpl implements NoteRepository {

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> noteArrayList = new ArrayList<>();
        noteArrayList.add(new Note(1, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(2, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(3, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(4, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(5, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));
        return noteArrayList;
    }
}