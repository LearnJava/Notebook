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

        noteArrayList.add(new Note(6, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(7, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(8, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(9, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(10, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        noteArrayList.add(new Note(11, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(12, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(13, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(14, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(15, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        noteArrayList.add(new Note(16, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(17, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(18, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(19, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(20, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        noteArrayList.add(new Note(21, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(22, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(23, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(24, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(25, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        noteArrayList.add(new Note(26, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(27, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(28, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(29, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(30, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        noteArrayList.add(new Note(31, "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(32, "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(33, "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(34, "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(35, "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        for (int i = 0; i < 10000; i++) {
            noteArrayList.add(new Note(35 + i, "Заметка для Кости" + i, "Доделать ДЗ по фрагментам", new Date().getTime()));
        }
        return noteArrayList;
    }
}