package com.example.hatrick

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class GamesListActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerview : RecyclerView
    private lateinit var gameArrayList : ArrayList<Reservation>
    private lateinit var gameAdapter: GameAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)

        recyclerview = findViewById(R.id.gameList)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        val card = intent.getStringExtra("card").toString()
        gameArrayList = arrayListOf()
        gameAdapter = GameAdapter(this,gameArrayList, card)
        recyclerview.adapter = gameAdapter

        EventChangeListener()

    }
    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        val card = intent.getStringExtra("sportType")
        db.collection("Reservations")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            gameArrayList.add(dc.document.toObject(Reservation::class.java))
                        }
                    }
                    gameAdapter.notifyDataSetChanged()
                }
            })
    }
}