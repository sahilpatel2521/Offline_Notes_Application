package com.example.basicnote.data.dao

import androidx.room.*
import com.example.basicnote.data.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("Select * from notes ORDER BY date Desc")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}