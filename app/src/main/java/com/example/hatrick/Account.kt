package com.example.hatrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import com.example.hatrick.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Account : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val UserFireData = FirebaseFirestore.getInstance()
        UserFireData.collection("users").whereEqualTo("customerID", getCurrentUserID())
            .get().addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        binding.firstNameBox.append(doc.data.getValue("firstName") as CharSequence?)
                        binding.lastNameBox.append(doc.data.getValue("lastName") as CharSequence?)
                        binding.phoneBox.append(doc.data.getValue("phoneNumber") as CharSequence?)
                        binding.emailBox.append(doc.data.getValue("email") as CharSequence?)

                    }

                }

            }
    }
}



//fun getCurrentUserID(): String {
//    val currentUser = FirebaseAuth.getInstance().currentUser
//    var currentUserID = ""
//    if (currentUser != null) {
//        currentUser.uid.also { currentUserID = it }
//    }
//    return currentUserID
//}