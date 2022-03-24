package com.dogancanemek.takenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnDeleteIconClicked, OnItemClicked {
    lateinit var recyclerView: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteAdapter = NoteAdapter(this, this, this)
        recyclerView.adapter = noteAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteAdapter.updateNotes(it)
            }
        })

        addButton.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDeleteIconClicked(note: Note) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(note.noteTitle)
        alert.setMessage("Are you sure you want to delete ${note.noteTitle}?")
        alert.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteNotes(note)
            Toast.makeText(this,"${note.noteTitle} has been deleted.", Toast.LENGTH_LONG).show()
        }
        alert.setNegativeButton("No") { _, _ ->
        }
        alert.show()
    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteID", note.id)
        startActivity(intent)
    }
}