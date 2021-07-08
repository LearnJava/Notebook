package ru.konstantin.notebook.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    public interface OnNoteClickedListener {
        void onNoteClickedListener(@NonNull Note note);
    }

    public interface OnNoteLongClickedListener {
        void onNoteLongClickedListener(@NonNull Note note, int index);
    }

    Fragment fragment;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    private OnNoteLongClickedListener longClickedListener;

    public OnNoteLongClickedListener getLongClickedListener() {
        return longClickedListener;
    }

    public void setLongClickedListener(OnNoteLongClickedListener longClickedListener) {
        this.longClickedListener = longClickedListener;
    }

    private final ArrayList<Note> notes = new ArrayList<>();


    public void setData(List<Note> toSet) {
        notes.clear();
        notes.addAll(toSet);
    }

    public int add(Note note) {
        notes.add(note);
        return notes.size() - 1;
    }

    public void update(Note note) {

        for (int i = 0; i < notes.size(); i++) {

            Note item = notes.get(i);

            if (item.getId().equals(note.getId())) {

                notes.remove(i);
                notes.add(i, note);
                return;
            }
        }
    }

  public void remove(Note longClickedNote) {
        notes.remove(longClickedNote);
    }

    private OnNoteClickedListener listener;

    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteView.setText(note.getDescription());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView noteView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(v -> {
                if (getListener() != null) {
                    getListener().onNoteClickedListener(notes.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();

                    if (getLongClickedListener() != null) {
                        int index = getAdapterPosition();
                        getLongClickedListener().onNoteLongClickedListener(notes.get(index), index);
                    }

                    return true;
                }
            });

            noteView = itemView.findViewById(R.id.note_desc);
        }
    }
}
