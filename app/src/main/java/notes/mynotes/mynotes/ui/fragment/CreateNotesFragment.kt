package notes.mynotes.mynotes.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import notes.mynotes.mynotes.R
import notes.mynotes.mynotes.databinding.FragmentCreateNotesBinding
import notes.mynotes.mynotes.model.Notes
import notes.mynotes.mynotes.viewmodel.NotesViewModel
import java.util.*


class CreateNotesFragment : Fragment() {
    private lateinit var binding: FragmentCreateNotesBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNotesBinding.inflate(inflater, container, false)

        toolBarSetUp()
        setupPrioritySelection()
        binding.btnSaveNotes.setOnClickListener { createNotes() }

        return binding.root
    }

    private fun setupPrioritySelection() {
        val priorityViews = listOf(binding.pMost, binding.pMedium, binding.pLeast)
        val priorities = listOf("1", "2", "3")

        priorityViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                priority = priorities[index]

                // Reset selection state for all views
                priorityViews.forEach { it.setBackgroundResource(R.drawable.notselected) }
                textView.setBackgroundResource(R.drawable.selectedbackground)
            }
        }
    }


    private fun createNotes() {
        val title = binding.edtTitle.text.toString().trim()
        val subTitle = binding.edtSubtitle.text.toString().trim()
        val note = binding.edtNotes.text.toString().trim()
        val notesDate: String = DateFormat.format("d MMMM yyyy", Date().time).toString()

        if (title.isEmpty() || note.isEmpty()) {
            binding.edtTitle.error = "Title is required"
            binding.edtNotes.error = "Note cannot be empty"
            return
        }

        val data = Notes(null, title, subTitle, note, notesDate, priority)
        viewModel.insertNotes(data)
        findNavController().navigate(R.id.action_createNotesFragment_to_homeFragment)
    }

    private fun toolBarSetUp() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbarCreateNotes)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_stack)
                title = ""
            }
        }
        binding.toolbarCreateNotes.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
