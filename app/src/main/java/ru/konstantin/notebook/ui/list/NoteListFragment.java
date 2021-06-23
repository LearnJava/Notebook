package ru.konstantin.notebook.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;
import ru.konstantin.notebook.repository.NoteRepository;
import ru.konstantin.notebook.repository.NoteRepositoryImpl;

public class NoteListFragment extends Fragment {

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private NoteRepository noteRepository;

    private OnNoteClicked onNoteClicked;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClicked) {
            onNoteClicked = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        onNoteClicked = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteRepository = new NoteRepositoryImpl();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout linearLayout = view.findViewById(R.id.note_list_container);

        List<Note> notes = noteRepository.getNotes();

        for (Note note: notes) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, linearLayout, false);
            itemView.setOnClickListener(v -> {
                if (onNoteClicked != null) {
                    onNoteClicked.onNoteClicked(note);
                }
            });
            TextView noteView = itemView.findViewById(R.id.note_id);
            noteView.setText(getResources().getString(R.string.note_template, note.getId()));

            linearLayout.addView(noteView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
