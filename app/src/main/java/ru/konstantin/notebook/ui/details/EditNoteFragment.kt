package ru.konstantin.notebook.ui.details

import android.content.Context
import androidx.fragment.app.FragmentActivity
import ru.konstantin.notebook.repository.NoteRepository
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import ru.konstantin.notebook.R
import ru.konstantin.notebook.repository.NoteFirestoreRepositoryImpl
import android.widget.EditText
import android.widget.DatePicker
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.konstantin.notebook.entity.Note
import ru.konstantin.notebook.repository.Callback
import java.util.*

class EditNoteFragment : Fragment() {
    var myContext: FragmentActivity? = null
    private var noteRepository: NoteRepository? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
        noteRepository = NoteFirestoreRepositoryImpl()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note: Note = arguments?.getParcelable(ARG_NOTE) ?: Note()
        val noteDesc = view.findViewById<EditText>(R.id.description_edit)
        val noteText = view.findViewById<EditText>(R.id.note_text_edit)
        val datePicker = view.findViewById<DatePicker>(R.id.note_date_edit)
        val calendar = Calendar.getInstance()
        calendar.time = Date(note.noteDate)
        datePicker.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ) { view, year, monthOfYear, dayOfMonth -> }
        noteDesc.setText(note.description)
        noteText.setText(note.noteText)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_apply)
        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_apply) {
                val bundle = Bundle()
//                val note: Note = arguments!!.getParcelable(ARG_NOTE)
                note.description = noteDesc.text.toString()
                note.noteText = noteText.text.toString()
                val calendar: Calendar =
                    GregorianCalendar(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                note.noteDate = calendar.timeInMillis
                if (note.id == null) {
                    bundle.putBoolean(IS_UPDATE, false)
                    noteRepository!!.add(note, object : Callback<Note> {
                        override fun onSuccess(result: Note) {
                            bundle.putParcelable(ARG_NOTE, result)
                            parentFragmentManager.setFragmentResult(UPDATE_RESULT, bundle)
                            // Общение между фрагментами (результат работы фрагмента)...
                            myContext!!.supportFragmentManager.popBackStack()
                        }
                    })
                } else {
                    //вместо метода update. Удаляем старую заметку и добавляем новую,
                    // но так как сортируем мы по полю даты, то появится заметка в адаптере на том же месте
                    bundle.putBoolean(IS_UPDATE, true)
                    noteRepository!!.remove(note, object : Callback<Note> {
                        override fun onSuccess(result: Note) {}
                    })
                    noteRepository!!.add(note, object : Callback<Note> {
                        override fun onSuccess(result: Note) {
                            bundle.putParcelable(ARG_NOTE, result)
                            parentFragmentManager.setFragmentResult(UPDATE_RESULT, bundle)
                            // Общение между фрагментами (результат работы фрагмента)...
                            myContext!!.supportFragmentManager.popBackStack()
                        }
                    })
                }
                return@OnMenuItemClickListener true
            }
            false
        })
    }

    companion object {
        const val TAG = "EditNoteFragment"
        const val ARG_NOTE = "ARG_NOTE"
        const val UPDATE_RESULT = "UPDATE_RESULT"
        const val IS_UPDATE = "IS_UPDATE"

        @JvmStatic
        fun newInstance(note: Note?): EditNoteFragment {
            val fragment = EditNoteFragment()
            val args = Bundle()
            args.putParcelable(ARG_NOTE, note)
            fragment.arguments = args
            return fragment
        }
    }
}