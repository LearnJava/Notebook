package ru.konstantin.notebook.ui.list

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.notebook.R
import ru.konstantin.notebook.entity.Note
import ru.konstantin.notebook.repository.Callback
import ru.konstantin.notebook.repository.NoteFirestoreRepositoryImpl
import ru.konstantin.notebook.repository.NoteRepository
import ru.konstantin.notebook.ui.details.EditNoteFragment
import ru.konstantin.notebook.ui.details.EditNoteFragment.Companion.newInstance
import ru.konstantin.notebook.ui.notes.NotesAdapter
import ru.konstantin.notebook.ui.notes.NotesAdapter.OnNoteClickedListener
import ru.konstantin.notebook.ui.notes.NotesAdapter.OnNoteLongClickedListener
import java.util.*

class NoteListFragment : Fragment() {
    interface OnNoteClicked {
        fun onNoteClicked(note: Note?)
    }

    private var isLoading = false
    private val progressBar: ProgressBar? = null
    var myContext: FragmentActivity? = null
    private val noteRepository: NoteRepository = NoteFirestoreRepositoryImpl()
    private var onNoteClicked: OnNoteClicked? = null
    private var notesAdapter: NotesAdapter? = null
    private var longClickedIndex = 0
    private var longClickedNote: Note = Note()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
        if (context is OnNoteClicked) {
            onNoteClicked = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onNoteClicked = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesAdapter = NotesAdapter(this)
        notesAdapter!!.longClickedListener = object : OnNoteLongClickedListener {
            override fun onNoteLongClickedListener(note: Note, index: Int) {
                longClickedIndex = index
                longClickedNote = note
            }
        }

        // Обмен между фрагментами...
        parentFragmentManager.setFragmentResultListener(
            EditNoteFragment.UPDATE_RESULT,
            this,
            { requestKey, result ->
                if (!result.getBoolean(EditNoteFragment.IS_UPDATE)) {
                    val note: Note = result.getParcelable(EditNoteFragment.ARG_NOTE) ?:Note()
                    notesAdapter!!.notifyItemChanged(notesAdapter!!.add(note))
                    notesAdapter!!.notifyDataSetChanged()
                } else if (result.getBoolean(EditNoteFragment.IS_UPDATE)) {
                    val note: Note = result.getParcelable(EditNoteFragment.ARG_NOTE) ?: Note()
                    notesAdapter!!.update(note)
                    notesAdapter!!.notifyItemChanged(longClickedIndex)
                    notesAdapter!!.notifyDataSetChanged()
                }
            })
        // Обмен между фрагментами...
        isLoading = true
        noteRepository.getNotes(object : Callback<List<Note>> {
            override fun onSuccess(result: List<Note>) {
                notesAdapter!!.setData(result)
                notesAdapter!!.notifyDataSetChanged()
                isLoading = false
                if (progressBar != null) {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.notes_list)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        noteRepository.getNotes(object : Callback<List<Note>> {
            override fun onSuccess(result: List<Note>) {
                notesAdapter!!.setData(result)
                notesAdapter!!.notifyDataSetChanged()
                isLoading = false
                if (progressBar != null) {
                    progressBar.visibility = View.GONE
                }
            }
        })
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_2)
        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_add) {
                val note = Note()
                note.noteDate = Date().time
                myContext!!.supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(EditNoteFragment.TAG)
                    .add(R.id.container, newInstance(note))
                    .commit()
                return@OnMenuItemClickListener true
            }
            if (item.itemId == R.id.action_clear) {
                noteRepository.clear()
                notesAdapter!!.setData(emptyList())
                notesAdapter!!.notifyDataSetChanged()
                return@OnMenuItemClickListener true
            }
            false
        })
        recyclerView.adapter = notesAdapter
        notesAdapter!!.notifyDataSetChanged()
        notesAdapter!!.listener = object : OnNoteClickedListener {
            override fun onNoteClickedListener(note: Note) {
                if (onNoteClicked != null) {
                    onNoteClicked!!.onNoteClicked(note)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.notes_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit) {
            myContext!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(EditNoteFragment.TAG)
                .replace(R.id.container, newInstance(longClickedNote))
                .commit()
            return true
        }
        if (item.itemId == R.id.action_delete) {
            noteRepository.remove(longClickedNote, object : Callback<Note> {
                override fun onSuccess(result: Note) {
                    showAlertDeleteDialog()
                }
            })
            return true
        }
        return super.onContextItemSelected(item)
    }

    private fun showAlertDeleteDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_dialog)
            .setMessage(R.string.delete_message)
            .setPositiveButton(R.string.yes_delete) { dialog, which ->
                notesAdapter!!.remove(longClickedNote)
                notesAdapter!!.notifyItemRemoved(longClickedIndex)
            }
            .setNegativeButton(R.string.not_delete) { dialog, which -> }
        builder.show()
    }
}