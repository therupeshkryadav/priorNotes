package notes.mynotes.mynotes.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNotesBinding.inflate(layoutInflater)

        //toolbar setup
        toolBarSetUp()

        binding.pMost.setOnClickListener {
            priority = "1"
        }
        binding.pMedium.setOnClickListener {
            priority = "2"
        }
        binding.pLeast.setOnClickListener {
            priority = "3"
        }

        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {

        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubtitle.text.toString()
        val note = binding.edtNotes.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        val data = Notes(
            null,
            title = title,
            subTitle = subTitle,
            notes = note,
            date = notesDate as String,
            priority
        )
        viewModel.insertNotes(data)

       // Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
        findNavController().navigate(
            CreateNotesFragmentDirections.actionCreateNotesFragmentToHomeFragment()
        )
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCreateNotes)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_stack)
            binding.toolbarCreateNotes.title = ""
        }
        binding.toolbarCreateNotes.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}