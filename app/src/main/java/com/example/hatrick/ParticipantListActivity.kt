package com.example.hatrick

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ParticipantListActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerview : RecyclerView
    private lateinit var gameArrayList : ArrayList<Participant>
    private lateinit var gameAdapter: ParticipantAdapter
    var Currentday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    var Currentmonth = (Calendar.getInstance().get(Calendar.MONTH).toString().toInt()+1).toString()
    val Currentyear = Calendar.getInstance().get(Calendar.YEAR).toString()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
        recyclerview = findViewById(R.id.gameList)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        var card :String = ""
        val act = intent.getStringExtra("act").toString()
        if (act == "history")
        {
            card = "Football"
        }
        else if (act == "upcoming")
        {
            card = "Football"
        }
        gameArrayList = arrayListOf()
        gameAdapter = ParticipantAdapter(this,gameArrayList, card , act)
        recyclerview.adapter = gameAdapter
        EventChangeListener()
    }
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        val card = intent.getStringExtra("card")
        val act = intent.getStringExtra("act")
        if(Currentday.length<2){
            Currentday="0"+Currentday
        }
        if(Currentmonth.length<2){
            Currentmonth="0"+Currentmonth
        }
        val currentdate = "$Currentday/$Currentmonth/$Currentyear"
        if (act == "history")
        {
            db.collection("Participant").whereEqualTo("userID", getCurrentUserID()).whereEqualTo("public", "true").whereLessThan("reservationDate",currentdate)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                gameArrayList.add(dc.document.toObject(Participant::class.java))
                            }
                        }
                        gameAdapter.notifyDataSetChanged()
                    }
                })
        }
        else if (act == "upcoming")
        {
            db.collection("Participant").whereEqualTo("userID", getCurrentUserID()).whereEqualTo("public", "true").whereGreaterThanOrEqualTo("reservationDate",currentdate)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                gameArrayList.add(dc.document.toObject(Participant::class.java))
                            }
                        }
                        gameAdapter.notifyDataSetChanged()
                    }
                })
        }
    }
}