package ru.konstantin.notebook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ru.konstantin.notebook.R;
import ru.konstantin.notebook.entity.Note;
import ru.konstantin.notebook.ui.details.NoteDetailsActivity;
import ru.konstantin.notebook.ui.details.NoteDetailsFragment;
import ru.konstantin.notebook.ui.list.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNoteClicked(Note note) {

        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);
//        Toast.makeText(this, note.getNote(), Toast.LENGTH_SHORT).show();

        if (isLandscape) {
//            Вызываем фрагмент
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_details_fragment, NoteDetailsFragment.newInstance(note))
                    .commit();
        } else {
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra(NoteDetailsActivity.ARG_NOTE, note);
            startActivity(intent);
        }
    }

}