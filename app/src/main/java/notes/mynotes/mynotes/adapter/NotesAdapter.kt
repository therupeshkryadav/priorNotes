package notes.mynotes.mynotes.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import notes.mynotes.mynotes.R
import notes.mynotes.mynotes.databinding.ItemNotesBinding
import notes.mynotes.mynotes.model.Notes
import notes.mynotes.mynotes.ui.fragment.HomeFragmentDirections

class NotesAdapter(val context: Context, val list: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNotesBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = list[position]

        holder.binding.apply {
            tvTitle.text = item.title
            tvSubtitle.text = item.subTitle
            tvDate.text = item.date
        }
        when (item.priority) {
            "1" -> {
                holder.binding.priority.setBackgroundColor("#850505".toColorInt())
            }
            "2" -> {
                holder.binding.priority.setBackgroundColor("#054B85".toColorInt())
            }
            "3" -> {
                holder.binding.priority.setBackgroundColor("#058305".toColorInt())
            }
        }
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(item)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}