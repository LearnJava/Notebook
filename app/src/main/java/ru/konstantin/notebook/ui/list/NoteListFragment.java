package ru.konstantin.notebook.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;
import ru.konstantin.notebook.repository.NoteRepository;
import ru.konstantin.notebook.repository.NoteRepositoryImpl;
import ru.konstantin.notebook.ui.details.EditNoteFragment;
import ru.konstantin.notebook.ui.notes.NotesAdapter;

public class NoteListFragment extends Fragment {

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    FragmentActivity myContext;

    private NoteRepository noteRepository;
    private OnNoteClicked onNoteClicked;
    private NotesAdapter notesAdapter;

    private int longClickedIndex;
    private Note longClickedNote;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
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

        notesAdapter = new NotesAdapter(this);
        noteRepository = new NoteRepositoryImpl();

        notesAdapter.setLongClickedListener(new NotesAdapter.OnNoteLongClickedListener() {
            @Override
            public void onNoteLongClickedListener(@NonNull  Note note, int index) {
                longClickedIndex = index;
                longClickedNote = note;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        List<Note> notes = noteRepository.getNotes();
        notesAdapter.setDate(notes);
        Toolbar toolbar = view.findViewById(R.id.toolbar_2);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
//                    noteRepository.add("Заметка для кота", "не забыть поспать");

                    int index = notesAdapter.add(noteRepository.add("Заметка для кота", "не забыть поспать"));
                    notesAdapter.notifyItemInserted(index);
                    recyclerView.scrollToPosition(index);
                    return true;
                }

                if (item.getItemId() == R.id.action_clear) {
                    noteRepository.clear();
                    notesAdapter.setDate(Collections.emptyList());
                    notesAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        recyclerView.setAdapter(notesAdapter);

        notesAdapter.notifyDataSetChanged();

        notesAdapter.setListener(note -> {
            if (onNoteClicked != null) {
                onNoteClicked.onNoteClicked(note);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull  MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {

            myContext.getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(EditNoteFragment.TAG)
                    .replace(R.id.container, EditNoteFragment.newInstance(longClickedNote))
                    .commit();
            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            noteRepository.remove(longClickedNote);

            notesAdapter.remove(longClickedNote);
            notesAdapter.notifyItemRemoved(longClickedIndex);

            return true;
        }

        return super.onContextItemSelected(item);

    }
}
