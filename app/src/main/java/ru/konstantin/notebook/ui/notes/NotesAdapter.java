package ru.konstantin.notebook.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    public interface OnNoteClickedListener {
        void onNoteClickedListener(@NonNull Note note);
    }

    private final ArrayList<Note> notes = new ArrayList<>();

    public void setDate(List<Note> toSet) {
        notes.clear();
        notes.addAll(toSet);
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

        holder.noteView.setText("My note number " + note.getId());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView noteView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (getListener() != null) {
                    getListener().onNoteClickedListener(notes.get(getAdapterPosition()));
                }
            });
            noteView = itemView.findViewById(R.id.note_id);
        }
    }

}
