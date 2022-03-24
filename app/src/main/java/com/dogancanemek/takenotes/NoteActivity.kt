package com.dogancanemek.takenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    lateinit var editTitle: EditText
    lateinit var editNote: EditText
    lateinit var saveButton: Button
    var noteID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        editTitle = findViewById(R.id.editTitle)
        editNote = findViewById(R.id.editNote)
        saveButton = findViewById(R.id.saveButton)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[NoteViewModel::class.java]

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)
            saveButton.text = "Update Note"
            editTitle.setText(noteTitle)
            editNote.setText(noteDescription)
        } else {
            saveButton.text = "Save Note"
        }

        saveButton.setOnClickListener {
            val noteTitle = editTitle.text.toString()
            val noteDescription = editNote.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val timeStamp = SimpleDateFormat("dd, MM, yyyy - HH:mm:ss", Locale.getDefault())
                    val currentTime = timeStamp.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentTime)
                    updateNote.id = noteID
                    viewModel.updateNotes(updateNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val timeStamp = SimpleDateFormat("dd, MM, yyyy - HH:mm:ss", Locale.getDefault())
                    val currentTime = timeStamp.format(Date())
                    viewModel.addNotes(Note(noteTitle, noteDescription, currentTime))
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}