package notes.mynotes.mynotes.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var binding: FragmentEditNotesBinding
    var priority: String = "1"
    private val args: EditNotesFragmentArgs by navArgs()
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater)


        //toolbar setup
        toolBarSetUp()

        val data = args.data

        showDefaultData(data)

        binding.ivDelete.setOnClickListener {
            deleteNote()
        }

        return binding.root
    }

    private fun deleteNote() {

        val bottomSheet = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        bottomSheet.setContentView(R.layout.dialog_delete)

        val btn_yes = bottomSheet.findViewById<TextView>(R.id.btn_yes)
        val btn_no = bottomSheet.findViewById<TextView>(R.id.btn_no)

        btn_yes?.setOnClickListener {
            viewModel.deleteNotes(args.data?.id!!)
            val action = EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment()
            findNavController().navigate(action)
            bottomSheet.dismiss()
        }
        btn_no?.setOnClickListener {
            bottomSheet.dismiss()
        }

        bottomSheet.show()


    }

    private fun showDefaultData(data: Notes?) {
        binding.edtTitle.setText(data?.title)
        binding.edtSubtitle.setText(data?.subTitle)
        binding.edtNotes.setText(data?.notes)
        when (data?.priority) {
            "1" -> {
                priority = "1"
                binding.pRed.setImageResource(R.drawable.ic_done)
                binding.pBlue.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.pBlue.setImageResource(R.drawable.ic_done)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.pGreen.setImageResource(R.drawable.ic_done)
                binding.pRed.setImageResource(0)
                binding.pBlue.setImageResource(0)
            }
        }

        binding.pRed.setOnClickListener {
            priority = "1"
            binding.pRed.setImageResource(R.drawable.ic_done)
            binding.pBlue.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }
        binding.pBlue.setOnClickListener {
            priority = "2"
            binding.pBlue.setImageResource(R.drawable.ic_done)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }
        binding.pGreen.setOnClickListener {
            priority = "3"
            binding.pGreen.setImageResource(R.drawable.ic_done)
            binding.pRed.setImageResource(0)
            binding.pBlue.setImageResource(0)
        }

        binding.btnEditSaveNotes.setOnClickListener {
            updateNote(it)
        }


    }

    private fun updateNote(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubtitle.text.toString()
        val note = binding.edtNotes.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        val data = Notes(
            id = args.data?.id,
            title = title,
            subTitle = subTitle,
            notes = note,
            date = notesDate as String,
            priority
        )

        viewModel.updateNotes(data)
        // Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
        findNavController().navigate(
            EditNotesFragmentDirections.actionEditNotesFragmentToHomeFragment()
        )
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarEditNotes)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_stack)
            binding.toolbarEditNotes.title = ""
        }
        binding.toolbarEditNotes.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}