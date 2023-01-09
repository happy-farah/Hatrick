package com.example.hatrick

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.type.Color

class FieldListActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerview : RecyclerView
    private lateinit var FieldArrayList : ArrayList<Field>
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fieldlist)
        recyclerview = findViewById(R.id.FieldList)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        FieldArrayList = arrayListOf()
        val card = intent.getStringExtra("card").toString()
        myAdapter = MyAdapter(this,FieldArrayList, card)
        recyclerview.adapter = myAdapter
        EventChangeListener()
    }
    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        val FieldIntent = intent
        val card = intent.getStringExtra("card")
        db.collection("Fields").whereArrayContains("sportType", "$card")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                this@FieldListActivity.title = FieldIntent.getStringExtra("fieldName").toString()
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
//
//
//package com.example.hatrick
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.firestore.DocumentChange
//import com.google.firebase.firestore.EventListener
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestoreException
//import com.google.firebase.firestore.QuerySnapshot
//
//class Football : AppCompatActivity() {
//
//    private lateinit var db : FirebaseFirestore
//    private lateinit var recyclerview : RecyclerView
//    private lateinit var FieldArrayList : ArrayList<Field>
//    private lateinit var myAdapter: MyAdapter
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_football)
//
//        recyclerview = findViewById(R.id.FieldList)
//        recyclerview.layoutManager = LinearLayoutManager(this)
//        recyclerview.setHasFixedSize(true)
//
//        FieldArrayList = arrayListOf()
//        myAdapter = MyAdapter(FieldArrayList)
//        recyclerview.adapter = myAdapter
//
//        EventChangeListener()
//
//
//    }
//
//    private fun EventChangeListener(){
//        db = FirebaseFirestore.getInstance()
//        db.collection("Fields").
//        addSnapshotListener(object : EventListener<QuerySnapshot>{
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                if (error != null) {
//                    Log.e("Firestore Error", error.message.toString())
//                    return
//                }
//                for (dc: DocumentChange in value?.documentChanges!!) {
//                    if (dc.type == DocumentChange.Type.ADDED) {
//                        FieldArrayList.add(dc.document.toObject(Field::class.java))
//                    }
//                }
//                myAdapter.notifyDataSetChanged()
//            }
//        })
//
//        var adapter = MyAdapter(FieldArrayList)
//        recyclerview.adapter = adapter
//        adapter.setOnItemClickListener(object  : MyAdapter.onItemClickListener{
//            override fun onItemClick(position: Int) {
//                val intent = Intent (this@Football , FieldInfo :: class.java)
//                startActivity(intent)
////                Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}