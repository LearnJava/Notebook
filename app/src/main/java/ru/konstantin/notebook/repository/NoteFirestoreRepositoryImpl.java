package ru.konstantin.notebook.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ru.konstantin.notebook.entity.Note;

public class NoteFirestoreRepositoryImpl implements NoteRepository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static final String NOTES = "notes";
    public static final String NOTE_DATE = "noteDate";
    public static final String DESC = "description";
    public static final String NOTE_TEXT = "noteText";

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        firebaseFirestore.collection(NOTES)
                .orderBy(NOTE_DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            ArrayList<Note> result = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String noteText = (String) document.get(NOTE_TEXT);
                                String desc = (String) document.get(DESC);
                                Long longDate = Long.parseLong(document.get(NOTE_DATE).toString());
                                Date noteDate = new Date(longDate);

                                result.add(new Note(document.getId(), desc, noteText, noteDate.getTime()));
                            }

                            callback.onSuccess(result);

                        } else {
                            task.getException();
                        }
                    }
                });
    }

    @Override
    public void add(Note note, Callback<Note> callback) {

        HashMap<String, Object> data = new HashMap<>();

        data.put(DESC, note.getDescription());
        data.put(NOTE_TEXT, note.getNoteText());
        data.put(NOTE_DATE, note.getNoteDate());

        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            note.setId(task.getResult().getId());
                            callback.onSuccess(note);
                        }
                    }
                });
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(Note note, Callback<Note> callback) {
        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(note);
                        }
                    }
                });
    }

    @Override
    public Note edit(Note note, String desc, String noteText, Date date) {
        return null;
    }


}
