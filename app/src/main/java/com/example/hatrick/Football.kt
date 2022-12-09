package com.example.hatrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Football : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football)

        val fCard = findViewById<CardView>(R.id.fieldCard)
        fCard.setOnClickListener {
            val intent = Intent(this, Field::class.java)
            startActivity(intent)
        }
        val UserFireData = FirebaseFirestore.getInstance()
        UserFireData.collection("Fields")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        val fieldName = findViewById<TextView>(R.id.fieldName)
                        fieldName.setText(doc.data.getValue("fieldName") as CharSequence?)
                        val location = findViewById<TextView>(R.id.fieldLocation)
                        location.setText(doc.data.getValue("address") as CharSequence?)

                    }
                }
            }
          }
}