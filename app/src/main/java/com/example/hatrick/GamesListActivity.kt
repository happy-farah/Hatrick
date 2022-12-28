package com.example.hatrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GamesListActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerview : RecyclerView
    private lateinit var FieldArrayList : ArrayList<Field>
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
    }
}