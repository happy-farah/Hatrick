package com.example.hatrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ArSignin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_signin)

        val createAcc = findViewById<TextView>(R.id.ArCreateAcc)
        val changeLang = findViewById<TextView>(R.id.EngLab)


        createAcc.setOnClickListener {
            val intent = Intent(this,ArCreateAcc::class.java)
            startActivity(intent)
        }
        changeLang.setOnClickListener{
            val intent = Intent(this , Signin::class.java)
            startActivity(intent)
        }
    }
}