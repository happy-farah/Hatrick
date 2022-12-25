package com.example.hatrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val Save = findViewById<Button>(R.id.saveBtn)
        Save.setOnClickListener {
            val intent = Intent(this,EditProfile::class.java)
            startActivity(intent)
        }
        val changePass = findViewById<Button>(R.id.changePass)
        changePass.setOnClickListener {
            val intent = Intent(this,changePass::class.java)
            startActivity(intent)
        }
        firebaseAuth = FirebaseAuth.getInstance()
        val UserFireData = FirebaseFirestore.getInstance()
        UserFireData.collection("users").whereEqualTo("customerID", getCurrentUserID())
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        val FN = findViewById<EditText>(R.id.firstNameBox)
                        FN.setText(doc.data.getValue("firstName") as CharSequence?)
                        val LN = findViewById<EditText>(R.id.lastNameBox)
                        LN.setText(doc.data.getValue("lastName") as CharSequence?)
//                        val DOB =findViewById<EditText>(R.id.birthDate)
//                        DOB.setText(doc.data.getValue("dateOfBirth") as CharSequence?)
//                        val Gender =findViewById<TextView>(R.id.gender)
//                        Gender.setText(doc.data.getValue("gender")as CharSequence?)
                        val PN = findViewById<EditText>(R.id.phoneBox)
                        PN.setText(doc.data.getValue("phoneNumber") as CharSequence?)
                        val EM =findViewById<EditText>(R.id.emailBox)
                        EM.setText(doc.data.getValue("email") as CharSequence?)
                    }
                }
            }
    }
    }
