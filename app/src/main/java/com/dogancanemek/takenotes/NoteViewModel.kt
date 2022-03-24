package com.dogancanemek.takenotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    lateinit var allNotes: LiveData<List<Note>>
    lateinit var repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun addNotes(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun updateNotes(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun deleteNotes(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

}