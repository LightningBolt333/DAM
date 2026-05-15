package com.notes.notesproxmlviews

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.Query
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    var addNoteBtn: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var menuBtn: ImageButton? = null
    var noteAdapter: NoteAdapter? = null
    var noteList: MutableList<Note> = mutableListOf()
    var docIdList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNoteBtn = findViewById(R.id.add_note_btn)
        recyclerView = findViewById(R.id.recyler_view)
        menuBtn = findViewById(R.id.menu_btn)

        noteAdapter = NoteAdapter(noteList, docIdList) { note, docId ->
            val intent = Intent(this, NoteDetailsActivity::class.java)
            intent.putExtra("title", note.getTitle())
            intent.putExtra("content", note.getContent())
            intent.putExtra("docId", docId)
            intent.putExtra("imageBase64", note.getImageBase64())
            startActivity(intent)
        }
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = noteAdapter

        addNoteBtn!!.setOnClickListener {
            startActivity(Intent(this, NoteDetailsActivity::class.java))
        }

        menuBtn!!.setOnClickListener { showMenu() }

        loadNotes()
    }

    fun loadNotes() {
        Utility.getCollectionReferenceForNotes()
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) return@addSnapshotListener

                noteList.clear()
                docIdList.clear()

                for (doc in snapshots.documents) {
                    val note = doc.toObject(Note::class.java)
                    if (note != null) {
                        noteList.add(note)
                        docIdList.add(doc.id)
                    }
                }
                noteAdapter!!.notifyDataSetChanged()
            }
    }
    fun showMenu() {
        val popupMenu = android.widget.PopupMenu(this@MainActivity, menuBtn)

        popupMenu.show()
    }
}

class NoteAdapter(
    private val notes: MutableList<Note>,
    private val docIds: MutableList<String>,
    private val onClick: (Note, String) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title_text_view)
        val content: TextView = itemView.findViewById(R.id.note_content_text_view)
        val timestamp: TextView = itemView.findViewById(R.id.note_date_text_view)
        val noteImage: ImageView = itemView.findViewById(R.id.note_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val docId = docIds[position]

        holder.title.text = note.getTitle()
        holder.content.text = note.getContent()
        holder.timestamp.text = Utility.timestampToString(note.getTimestamp())

        val imageBase64 = note.getImageBase64()
        if (!imageBase64.isNullOrEmpty()) {

            val imageBytes = android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT)
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            holder.noteImage.setImageBitmap(bitmap)
            holder.noteImage.visibility = View.VISIBLE
        } else {
            holder.noteImage.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onClick(note, docId)
        }
    }

    override fun getItemCount() = notes.size
}