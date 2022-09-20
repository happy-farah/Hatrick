package com.example.hatrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.hatrick.databinding.ActivityCreateAccBinding
import com.google.firebase.auth.FirebaseAuth

class ArCreateAcc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_create_acc)



        val signInLink = findViewById<TextView>(R.id.ArsignIn)

        signInLink.setOnClickListener {
            val intent = Intent(this , ArSignin::class.java)
            startActivity(intent)
        }


    }
}