package ru.konstantin.notebook.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.fragment.app.FragmentResultListener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

import java.util.Date;

import java.util.List;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;
import ru.konstantin.notebook.repository.Callback;
import ru.konstantin.notebook.repository.NoteFirestoreRepositoryImpl;
import ru.konstantin.notebook.repository.NoteRepository;
import ru.konstantin.notebook.ui.details.EditNoteFragment;
import ru.konstantin.notebook.ui.notes.NotesAdapter;

public class NoteListFragment extends Fragment {

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private boolean isLoading = false;

    private ProgressBar progressBar;

    FragmentActivity myContext;

    private final NoteRepository noteRepository = new NoteFirestoreRepositoryImpl();
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

        notesAdapter.setLongClickedListener(new NotesAdapter.OnNoteLongClickedListener() {
            @Override
            public void onNoteLongClickedListener(@NonNull Note note, int index) {
                longClickedIndex = index;
                longClickedNote = note;
            }
        });

        // Обмен между фрагментами...
        getParentFragmentManager().setFragmentResultListener(EditNoteFragment.UPDATE_RESULT, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (!result.getBoolean(EditNoteFragment.IS_UPDATE)) {

                    Note note = result.getParcelable(EditNoteFragment.ARG_NOTE);
                    notesAdapter.notifyItemChanged(notesAdapter.add(note));
                    notesAdapter.notifyDataSetChanged();
                } else if (result.getBoolean(EditNoteFragment.IS_UPDATE)) {

                    Note note = result.getParcelable(EditNoteFragment.ARG_NOTE);
                    notesAdapter.update(note);
                    notesAdapter.notifyItemChanged(longClickedIndex);
                    notesAdapter.notifyDataSetChanged();
                }
            }
        });
        // Обмен между фрагментами...

        isLoading = true;

        noteRepository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                notesAdapter.setData(result);
                notesAdapter.notifyDataSetChanged();

                isLoading = false;

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
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

        noteRepository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                notesAdapter.setData(result);
                notesAdapter.notifyDataSetChanged();

                isLoading = false;

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_2);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    Note note = new Note();
                    note.setNoteDate(new Date().getTime());
                    myContext.getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(EditNoteFragment.TAG)
                            .add(R.id.container, EditNoteFragment.newInstance(note))
                            .commit();
                    return true;
                }

                if (item.getItemId() == R.id.action_clear) {
                    noteRepository.clear();
                    notesAdapter.setData(Collections.emptyList());
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
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {

            myContext.getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(EditNoteFragment.TAG)
                    .replace(R.id.container, EditNoteFragment.newInstance(longClickedNote))
                    .commit();
            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            noteRepository.remove(longClickedNote, new Callback<Note>() {
                @Override
                public void onSuccess(Note result) {
                    notesAdapter.remove(longClickedNote);
                    notesAdapter.notifyItemRemoved(longClickedIndex);
                }
            });
            return true;
        }

        return super.onContextItemSelected(item);

    }
}
