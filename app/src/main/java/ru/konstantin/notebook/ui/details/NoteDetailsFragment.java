package ru.konstantin.notebook.ui.details;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;

public class NoteDetailsFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        noteDetailsFragment.setArguments(bundle);
        return noteDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView noteId = view.findViewById(R.id.id);
        TextView noteDesc = view.findViewById(R.id.description);
        TextView noteText = view.findViewById(R.id.note_text);
        TextView noteDate = view.findViewById(R.id.note_date);

        Note note = null;
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
            noteId.setText(String.valueOf(note.getId()));
            noteDesc.setText(note.getDescription());
            noteText.setText(note.getNoteText());
            noteDate.setText(String.valueOf(LocalDateTime.ofInstant(Instant.ofEpochMilli(note.getNoteDate()), ZoneId.systemDefault())));
        }
    }
}