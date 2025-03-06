package notes.mynotes.mynotes.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import notes.mynotes.mynotes.R
import notes.mynotes.mynotes.databinding.FragmentEditNotesBinding
import notes.mynotes.mynotes.model.Notes
import notes.mynotes.mynotes.viewmodel.NotesViewModel
import java.util.*

class EditNotesFragment : Fragment() {
    private var _binding: FragmentEditNotesBinding? = null
    private val binding get() = _binding!!

    private var priority: String = "1"
    private val args: EditNotesFragmentArgs by navArgs()
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditNotesBinding.inflate(inflater, container, false)

        // Setup toolbar
        toolBarSetUp()

        // Get note data from arguments
        val data = args.data
        showDefaultData(data)

        // Delete button click listener
        binding.ivDelete.setOnClickListener {
            deleteNote()
        }

        return binding.root
    }

    private fun deleteNote() {
        val bottomSheet = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        bottomSheet.setContentView(R.layout.dialog_delete)

        val btnYes = bottomSheet.findViewById<TextView>(R.id.btn_yes)
        val btnNo = bottomSheet.findViewById<TextView>(R.id.btn_no)

        btnYes?.setOnClickListener {
            args.data?.id?.let { noteId ->
                viewModel.deleteNotes(noteId)
                findNavController().navigate(EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment())
                bottomSheet.dismiss()
            } ?: Toast.makeText(requireContext(), "Error deleting note", Toast.LENGTH_SHORT).show()
        }

        btnNo?.setOnClickListener {
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }

    private fun showDefaultData(data: Notes?) {
        binding.edtTitle.setText(data?.title)
        binding.edtSubtitle.setText(data?.subTitle)
        binding.edtNotes.setText(data?.notes)

        priority = data?.priority ?: "1"

        setupPrioritySelection()
        binding.btnEditSaveNotes.setOnClickListener {
            updateNote()
        }
    }

    private fun setupPrioritySelection() {
        val priorityViews = listOf(binding.pMost, binding.pMedium, binding.pLeast)
        val priorities = listOf("1", "2", "3")

        // Set default background based on current priority
        priorityViews.forEachIndexed { index, textView ->
            textView.setBackgroundResource(if (priority == priorities[index]) R.drawable.selectedbackground else R.drawable.notselected)

            textView.setOnClickListener {
                priority = priorities[index]

                // Reset selection state for all views
                priorityViews.forEach { it.setBackgroundResource(R.drawable.notselected) }
                textView.setBackgroundResource(R.drawable.selectedbackground)
            }
        }
    }

    private fun updateNote() {
        val title = binding.edtTitle.text.toString().trim()
        val subTitle = binding.edtSubtitle.text.toString().trim()
        val note = binding.edtNotes.text.toString().trim()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", Date().time)

        // Input validation
        if (title.isBlank() || note.isBlank()) {
            Toast.makeText(requireContext(), "Title and Note cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedNote = Notes(
            id = args.data?.id,
            title = title,
            subTitle = subTitle,
            notes = note,
            date = notesDate.toString(),
            priority = priority
        )

        viewModel.updateNotes(updatedNote)
        findNavController().navigate(EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment())
    }

    private fun toolBarSetUp() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbarEditNotes)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_stack)
                title = ""
            }
        }
        binding.toolbarEditNotes.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Prevent memory leaks
    }
}
