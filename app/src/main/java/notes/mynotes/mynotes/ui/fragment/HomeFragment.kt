package notes.mynotes.mynotes.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import notes.mynotes.mynotes.R
import notes.mynotes.mynotes.adapter.NotesAdapter
import notes.mynotes.mynotes.databinding.FragmentHomeBinding
import notes.mynotes.mynotes.viewmodel.NotesViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //toolbar setup
        toolBarSetUp()

        // if notes are not in the list then show the emptyLayout

        val gridLayout = GridLayoutManager(requireContext(), 1)
        binding.rcvAllNotes.layoutManager = gridLayout


        viewModel.getAllNotes().observe(viewLifecycleOwner) { notesList ->
            binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)
        }

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.pMost.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }

        binding.pMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }
        binding.pLeast.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }

        binding.pAll.setOnClickListener {
            viewModel.getAllNotes().observe(viewLifecycleOwner) { notesList ->
                binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }



        return binding.root
    }

    private fun toolBarSetUp() {
        binding.toolbarHome.title = ""
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHome)
        if ((activity as AppCompatActivity).supportActionBar != null) {
        }
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_share -> {
                // Share app link
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Check out this amazing app Prior Notes!")
                    putExtra(Intent.EXTRA_TEXT, "Hey! Check out this awesome app Prior Notes: https://drive.google.com/file/d/1dEp6x6pf1SOwZ15ONV8hBl9-VbQS90FC/view?usp=sharing")
                }
                startActivity(Intent.createChooser(shareIntent, "Share via"))
                return true
            }

            R.id.mi_privacy -> {
                // Navigate to Privacy Policy Fragment
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_homeFragment_to_privacyPolicyFragment)
                return true
            }

            R.id.mi_feedback -> {
                // Send feedback via email
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:rupesh.official484@gmail.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Prior Notes App Feedback")
                    putExtra(Intent.EXTRA_TEXT, "Hello Rupesh, I would like to share some feedback about your app...")
                }
                startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}