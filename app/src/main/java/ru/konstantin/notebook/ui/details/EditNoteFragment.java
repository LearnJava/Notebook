package ru.konstantin.notebook.ui.details;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;
import ru.konstantin.notebook.repository.Callback;
import ru.konstantin.notebook.repository.NoteFirestoreRepositoryImpl;
import ru.konstantin.notebook.repository.NoteRepository;
import ru.konstantin.notebook.ui.list.NoteListFragment;
import ru.konstantin.notebook.ui.notes.NotesAdapter;

public class EditNoteFragment extends Fragment {

    public static final String TAG = "EditNoteFragment";
    private static final String ARG_NOTE = "ARG_NOTE";

    FragmentActivity myContext;
    private NotesAdapter notesAdapter;
    private NoteRepository noteRepository;


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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
        noteRepository = new NoteFirestoreRepositoryImpl();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.notes_list);

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

        Toolbar toolbar = view.findViewById(R.id.toolbar_apply);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_apply) {

//                    EditText noteDesc = view.findViewById(R.id.description_edit);
//                    EditText noteText = view.findViewById(R.id.note_text_edit);

                    Note note = new Note();
                    note.setDescription(noteDesc.getText().toString());
                    note.setNoteText(noteText.getText().toString());

                    Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    note.setNoteDate(calendar.getTimeInMillis());
                    myContext.getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(EditNoteFragment.TAG)
                            .add(R.id.container, NoteListFragment.newInstance())
                            .commit();
                    noteRepository.add(note, new Callback<Note>() {

                        @Override
                        public void onSuccess(Note result) {

                            int index = notesAdapter.add(result);

                            notesAdapter.notifyItemInserted(index);
                            notesAdapter.notifyDataSetChanged();

                            recyclerView.scrollToPosition(index);
                        }
                    });

                    return true;

                }
                return false;
            }
        });
    }
}