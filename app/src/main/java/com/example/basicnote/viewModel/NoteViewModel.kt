package com.example.basicnote.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicnote.data.dao.NoteDao
import com.example.basicnote.data.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteDao: NoteDao) : ViewModel() {

    val notes = noteDao.getAllNotes()
    val noteChannel = Channel<NotesEvent>()
    val notesEvent = noteChannel.receiveAsFlow()

    fun insertNote(note : Note) = viewModelScope.launch {
        noteDao.insertNote(note)
        noteChannel.send(NotesEvent.NavigationToNotesFragment)
    }
    fun updateNote(note : Note) = viewModelScope.launch {
        noteDao.updateNote(note)
        noteChannel.send(NotesEvent.NavigationToNotesFragment)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        noteDao.deleteNote(note)
        noteChannel.send(NotesEvent.ShowUndoSnackBar("Note Deleted SuccessFully",note))
    }

    sealed class NotesEvent{
        data class ShowUndoSnackBar(val msg :String, val note :Note) :NotesEvent()
        object NavigationToNotesFragment : NotesEvent()
    }

}