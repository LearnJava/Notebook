package ru.konstantin.notebook.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;

public class EditNoteFragment extends Fragment {

    public static final String TAG = "EditNoteFragment";
    private static final String ARG_NOTE = "ARG_NOTE";

    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = getArguments().getParcelable(ARG_NOTE);
        EditText noteDesc = view.findViewById(R.id.description_edit);
        EditText noteText = view.findViewById(R.id.note_text_edit);

        DatePicker datePicker = view.findViewById(R.id.note_date_edit);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(note.getNoteDate()));

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });

        noteDesc.setText(note.getDescription());
        noteText.setText(note.getNoteText());
    }
}