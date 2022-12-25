package com.example.hatrick

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.hatrick.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ForgetPassword : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<EditText>(R.id.passwordbox).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val pass = binding.passwordbox.text.toString()
                val check2 = binding.checkIcon2
                val check3 = binding.checkIcon3
                val check4 = binding.checkIcon4
                val check5 = binding.checkIcon5

                if (pass.length >= 8) {
                    check2.setColorFilter(Color.parseColor("#009900"))
                }
                if (pass.length < 8) {
                    check2.setColorFilter(Color.GRAY)
                }
                if (pass.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() != null) {
                    if (pass.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() != null) {
                        check3.setColorFilter(Color.parseColor("#009900"))
                    }
                    else{
                        check3.setColorFilter(Color.GRAY)
                    }
                }else{
                    check3.setColorFilter(Color.GRAY)
                }
                if (pass.filter { it.isDigit() }.firstOrNull() != null) {
                    check4.setColorFilter(Color.parseColor("#009900"))
                }
                else if (pass.filter { it.isDigit() }.firstOrNull() == null) {
                    check4.setColorFilter(Color.GRAY)
                }
                if (pass.filter { !it.isLetterOrDigit() }.firstOrNull() != null) {
                    check5.setColorFilter(Color.parseColor("#009900"))
                }
                else if (pass.filter { !it.isLetterOrDigit() }.firstOrNull() == null) {
                    check5.setColorFilter(Color.GRAY)
                }
            }
        })

        findViewById<EditText>(R.id.confpasswordbox).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val pass = binding.passwordbox.text.toString()
                val confPass =binding.confpasswordbox.text.toString()
                val check = binding.checkIcon
                if (pass.equals(confPass)) {
                    check.setColorFilter(Color.parseColor("#009900"))
                }else{
                    check.setColorFilter(Color.GRAY)
                }

            }
        })

        binding.checkEmail.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()
            val email = binding.emailBox.text.toString()
            if (email.isNotEmpty()) {
                db.collection("users").whereEqualTo("email", email).get().addOnSuccessListener {
                    val passlinera = binding.passlinear
                    passlinera.setVisibility(View.VISIBLE)
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "User not exist",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        binding.saveBtn.setOnClickListener {
            val email = binding.emailBox.text.toString()
            val password = binding.passwordbox.text.toString()
            val confPass = binding.confpasswordbox.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && confPass.isNotEmpty())
                if (ValidatePassword()) {
                    if (password.equals(confPass)) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val User = User(
                                        getCurrentUserID(),
                                        email
                                    )
                                    val UserFireData = FirebaseFirestore.getInstance()
                                    UserFireData.collection("users")
                                        .document(getCurrentUserID())
                                        .set(
                                            User,
                                            SetOptions.merge()
                                        ).addOnSuccessListener {

                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Failed to save information",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    val intent = Intent(this, Signin::class.java)
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        this,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Password doesn't match",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
    fun ValidatePassword(): Boolean {
        val pass = binding.passwordbox.text.toString()
        if (pass.length >= 8) {
            if (pass.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() != null) {
                if (pass.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() != null) {
                    if (pass.filter { it.isDigit() }.firstOrNull() != null) {
                        if (pass.filter { !it.isLetterOrDigit() }.firstOrNull() != null) {
                            return true
                        } else {
                            return false
                        }
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            return false
        }
   }
}
