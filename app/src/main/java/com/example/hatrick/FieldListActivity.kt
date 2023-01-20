package com.example.hatrick

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.type.Color
import java.util.*
import kotlin.collections.ArrayList

class FieldListActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerview : RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var FieldArrayList : ArrayList<Field>
    private lateinit var myAdapter: MyAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fieldlist)
        recyclerview = findViewById(R.id.FieldList)
        searchView = findViewById(R.id.searchView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        FieldArrayList = arrayListOf()
        val card = intent.getStringExtra("card").toString()
        myAdapter = MyAdapter(this,FieldArrayList, card)
        recyclerview.adapter = myAdapter
        EventChangeListener()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }
    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Field>()
            for (i in FieldArrayList) {
                if (i.fieldName?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Field Found", Toast.LENGTH_SHORT).show()
            } else {
                myAdapter.setFilteredList(filteredList)
            }
        }
    }
    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        val FieldIntent = intent
        val card = intent.getStringExtra("card")
        db.collection("Fields").whereArrayContains("sportType", "$card")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        FieldArrayList.add(dc.document.toObject(Field::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}