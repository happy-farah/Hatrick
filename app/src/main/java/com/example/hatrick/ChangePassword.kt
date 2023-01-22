package com.example.hatrick

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hatrick.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePassword : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        firebaseAuth = FirebaseAuth.getInstance()
        findViewById<EditText>(R.id.passwordbox).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {
                if (ValidatePassword()){
                    findViewById<ImageView>(R.id.imageView).setColorFilter(Color.parseColor("#009900"))
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val pass = findViewById<EditText>(R.id.passwordbox).text.toString()
                val check2 = findViewById<ImageView>(R.id.checkIcon2)
                val check3 = findViewById<ImageView>(R.id.checkIcon3)
                val check4 = findViewById<ImageView>(R.id.checkIcon4)
                val check5 = findViewById<ImageView>(R.id.checkIcon5)
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
                val pass = findViewById<EditText>(R.id.passwordbox).text.toString()
                val confPass =findViewById<EditText>(R.id.confpasswordbox).text.toString()
                val check = findViewById<ImageView>(R.id.checkIcon)
                if (pass.equals(confPass)) {
                    check.setColorFilter(Color.parseColor("#009900"))
                }else{
                    check.setColorFilter(Color.GRAY)
                }
            }
        })
        findViewById<Button>(R.id.changePass).setOnClickListener {
            changePassword()
        }
        findViewById<TextView>(R.id.cancelBtn).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun changePassword() {
        val et_current_password =findViewById<EditText>(R.id.currentPasswordbox)
        val et_new_password =findViewById<EditText>(R.id.passwordbox)
        val et_confirm_password =findViewById<EditText>(R.id.confpasswordbox)
        if (et_current_password.text.isNotEmpty() &&
            et_new_password.text.isNotEmpty() &&
            et_confirm_password.text.isNotEmpty()) {
            if (et_new_password.text.toString().equals(et_confirm_password.text.toString())) {
                if (ValidatePassword()) {
                    val user = firebaseAuth.currentUser
                    if (user != null && user.email != null) {
                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, et_current_password.text.toString())
                        user?.reauthenticate(credential)
                            ?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    user?.updatePassword(et_new_password.text.toString())
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    this,
                                                    "Password changed successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                firebaseAuth.signOut()
                                                startActivity(Intent(this, Signin::class.java))
                                                finish()
                                            }else{
                                                Toast.makeText(this, "Incorrect Current Password", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                }else{
                                    Toast.makeText(this, "Incorrect Current Password", Toast.LENGTH_SHORT).show()
                                }
                            }?.addOnFailureListener{
                                Toast.makeText(this, "Incorrect Current Password", Toast.LENGTH_SHORT).show()

                            }
                    }
                } else {
                    Toast.makeText(this, "Password mismatching", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
        }
    }
    fun ValidatePassword(): Boolean {
        val pass = findViewById<EditText>(R.id.passwordbox).text.toString()
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