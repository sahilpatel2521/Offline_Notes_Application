package com.example.basicnote.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.basicnote.R
import com.example.basicnote.data.entity.Note
import com.example.basicnote.databinding.FragmenAddeditnoteBinding
import com.example.basicnote.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditFragment :Fragment(R.layout.fragmen_addeditnote) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by viewModels<NoteViewModel>()
        val binding = FragmenAddeditnoteBinding.bind(requireView())
        val args : AddEditFragmentArgs by navArgs()

        val note = args.note

        if (note!=null){
            binding.apply {

                titleEditText.setText(note.title)
                contentEditText.setText(note.content)

                floatingActionButton2.setOnClickListener {
                    val title = titleEditText.text.toString()
                    val content = contentEditText.text.toString()
                    val updateNote = note.copy(title = title, content = content , date = System.currentTimeMillis())
                    viewModel.updateNote(updateNote)
                }
            }
        }else{
            binding.apply {
                floatingActionButton2.setOnClickListener{
                    val title = titleEditText.text.toString()
                    val content = contentEditText.text.toString()
                    val note = Note(title = title, content = content, date = System.currentTimeMillis())
                    viewModel.insertNote(note)

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notesEvent.collect(){event ->
                if (event is NoteViewModel.NotesEvent.NavigationToNotesFragment){
                    val action = AddEditFragmentDirections.actionAddEditFragmentToNoteFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}