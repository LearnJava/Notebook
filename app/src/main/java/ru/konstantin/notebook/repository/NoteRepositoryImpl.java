package ru.konstantin.notebook.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ru.konstantin.notebook.entity.Note;

public class NoteRepositoryImpl implements NoteRepository {

    ArrayList<Note> noteArrayList = new ArrayList<>();

    public NoteRepositoryImpl() {
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Алёны", "Пропылесосить", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Лены", "Сходить погулять", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Димы", "Заниматься по азбуке", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Кости", "Доделать ДЗ по фрагментам", new Date().getTime()));
        noteArrayList.add(new Note(UUID.randomUUID().toString(), "Заметка для Тони", "Повторить финские слова", new Date().getTime()));

    }

    @Override
    public List<Note> getNotes() {

        return noteArrayList;
    }

    @Override
    public void clear() {
        noteArrayList.clear();
    }

    @Override
    public Note add(String desc, String noteText) {
        Note note = new Note(UUID.randomUUID().toString(), desc, noteText, new Date().getTime());
        noteArrayList.add(note);
        return note;
    }

    @Override
    public void remove(Note note) {
        noteArrayList.remove(note);
    }

    @Override
    public Note edit(Note note, String desc, String noteText, Date date) {

        for (int i = 0; i < noteArrayList.size(); i++) {

            Note item = noteArrayList.get(i);
            if (item.getId().equals(note.getId())) {

                String descToSet = note.getDescription();
                String noteTextToSet = note.getNoteText();
                Date dateToSet = new Date(note.getNoteDate());

                if (desc != null) {
                    note.setDescription(desc);
                }

                if (noteText != null) {
                    note.setNoteText(noteText);
                }

                if (date != null) {
                    note.setNoteDate(dateToSet.getTime());
                }

                Note newNote = new Note(note.getId(), descToSet, noteTextToSet, dateToSet.getTime());

                noteArrayList.remove(i);
                noteArrayList.add(i, newNote);

                return newNote;
            }
        }

        return note;
    }
}