package com.dogancanemek.takenotes

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    val context: Context,
    private val onItemClickedListener: OnItemClicked,
    private val onDeleteIconClickedListener: OnDeleteIconClicked,
    private var lastPosition: Int = -1
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.itemTitle)
        val timeStamp: TextView = item.findViewById(R.id.itemTimeStamp)
        val deleteButton: ImageView = item.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return NoteViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        setAnimation(holder.itemView, position)
        holder.title.text = allNotes[position].noteTitle
        holder.timeStamp.text = "Last Updated: ${allNotes[position].timeStamp}"
        holder.deleteButton.setOnClickListener {
            onDeleteIconClickedListener.onDeleteIconClicked(allNotes[position])
        }
        holder.itemView.setOnClickListener {
            onItemClickedListener.onItemClicked(allNotes[position])
        }
    }

    override fun getItemCount() = allNotes.size

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            animation.duration = 1000
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    fun updateNotes(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}

interface OnItemClicked {
    fun onItemClicked(note: Note)
}

interface OnDeleteIconClicked {
    fun onDeleteIconClicked(note: Note)
}