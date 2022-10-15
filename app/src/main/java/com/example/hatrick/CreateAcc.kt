package com.example.hatrick

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.hatrick.databinding.ActivityCreateAccBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class CreateAcc : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


//    fun basicReadWrite() {
//        // [START write_message]
//        // Write a message to the database
//        val database = Firebase.database
//        val myRef = database.getReference("https://hatrick-main-default-rtdb.firebaseio.com")
//
//        myRef.setValue("Hello, World!")
//        // [END write_message]
//
//        // [START read_message]
//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()
//                Log.d(TAG, "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
//        // [END read_message]
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)

        binding = ActivityCreateAccBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()



        val signInLink = findViewById<TextView>(R.id.signIn)

        signInLink.setOnClickListener {
            val intent = Intent(this , Signin::class.java)
            startActivity(intent)
        }


        binding.SignupBtn.setOnClickListener {
            val fname =binding.firstNameBox.text.toString()
            val lname =binding.lastNameBox.text.toString()
            val DDOB =binding.dayBox.text.toString()
            val MDOB =binding.monBox.text.toString()
            val YDOB =binding.yearBox.text.toString()
            val gender =binding.gender.checkedRadioButtonId.toString()
            val phone =binding.phoneBox.text.toString()
            val email =binding.emailBox.text.toString()
            val pass =binding.passwordbox.text.toString()
            val confPass =binding.confpasswordbox.text.toString()
            val name = fname + " " + lname
            val DOB = DDOB + MDOB + YDOB



            if (fname.isNotEmpty() && lname.isNotEmpty() && DDOB.isNotEmpty() && MDOB.isNotEmpty() && YDOB.isNotEmpty() && gender.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty())
            //if ( email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty())
            {
                if (pass.equals(confPass))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful)
                        {
                            database = FirebaseDatabase.getInstance().getReference("Users")
                            val User = User (getCurrentUserID(), fname,lname,phone)
                            val UserFireData = FirebaseFirestore.getInstance()
                            UserFireData.collection("users").document(getCurrentUserID()).set(User,
                                SetOptions.merge()).addOnSuccessListener {
                                binding.firstNameBox.text.clear()
                                binding.lastNameBox.text.clear()
                                binding.phoneBox.text.clear()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Nooooooooooooo", Toast.LENGTH_SHORT).show()

                            }

//                            database.child(fname).setValue(User).addOnSuccessListener {
//                                binding.firstNameBox.text.clear()
//                                binding.lastNameBox.text.clear()
//                                binding.phoneBox.text.clear()
//
//
//                            }
                            val intent = Intent(this , Signin::class.java)
                            startActivity(intent)

                        }
                        else{
                            Toast.makeText(this,it.exception.toString() ,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "check all", Toast.LENGTH_SHORT).show()
            }


        }
    }
}
fun getCurrentUserID(): String {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var currentUserID = ""
    if (currentUser != null) {
        currentUser.uid.also { currentUserID = it }
    }
    return currentUserID
}

