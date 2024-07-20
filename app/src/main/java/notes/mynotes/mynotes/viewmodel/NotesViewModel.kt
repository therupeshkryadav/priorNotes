package notes.mynotes.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import notes.mynotes.mynotes.database.NotesDatabase
import notes.mynotes.mynotes.model.Notes
import notes.mynotes.mynotes.repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun getAllNotes(): LiveData<List<Notes>> {
        return repository.getAllNotes()
    }

    fun insertNotes(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun deleteNotes(id: Int) {
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes) {
        repository.updateNotes(notes)
    }

    fun getHighNotes(): LiveData<List<Notes>> {
        return repository.getHighNotes()
    }

    fun getMediumNotes(): LiveData<List<Notes>> {
        return repository.getMediumNotes()
    }

    fun getLowNotes(): LiveData<List<Notes>> {
        return repository.getLowNotes()
    }
}