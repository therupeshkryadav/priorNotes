package notes.mynotes.mynotes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import notes.mynotes.mynotes.model.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE priority =1")
    fun getHighNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE priority =2")
    fun getMediumNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE priority =3")
    fun getHLowNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE FROM notes WHERE id=:id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes)

}