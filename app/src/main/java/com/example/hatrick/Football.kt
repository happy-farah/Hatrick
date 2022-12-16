package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class Football : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerview : RecyclerView
    private lateinit var FieldArrayList : ArrayList<Field>
    private lateinit var myAdapter: MyAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football)

        recyclerview = findViewById(R.id.FieldList)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        FieldArrayList = arrayListOf()
        myAdapter = MyAdapter(FieldArrayList)
        recyclerview.adapter = myAdapter

        EventChangeListener()
//        val fieldinfo = findViewById<CardView>(R.id.ffieldCard)
//        fieldinfo.setOnClickListener {
//            val intent = Intent(this , FieldInfo::class.java)
//            startActivity(intent)
//        }

    }

    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("Fields").
        addSnapshotListener(object : EventListener<QuerySnapshot>{
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