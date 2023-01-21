package com.example.hatrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class historyorupcoming : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historyorupcoming)
        val type = intent.getStringExtra("type")
        val upcoming = findViewById<CardView>(R.id.upComing)
        upcoming.setOnClickListener {
            if(type=="private"){
                val intent = Intent(this,GamesListActivity::class.java)
                intent.putExtra("act","upcoming")
                startActivity(intent)
            }else if(type == "public"){
                val intent = Intent(this,ParticipantListActivity::class.java)
                intent.putExtra("act","upcoming")
                startActivity(intent)
            }
        }
        val history = findViewById<CardView>(R.id.history)
        history.setOnClickListener {
            if(type=="private"){
                val intent = Intent(this,GamesListActivity::class.java)
                intent.putExtra("act","history")
                startActivity(intent)
            }else if(type == "public"){
                val intent = Intent(this,ParticipantListActivity::class.java)
                intent.putExtra("act","history")
                startActivity(intent)
            }
        }
    }
}