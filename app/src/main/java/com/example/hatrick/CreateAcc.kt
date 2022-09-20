package com.example.hatrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.hatrick.databinding.ActivityCreateAccBinding
import com.google.firebase.auth.FirebaseAuth

class CreateAcc : AppCompatActivity() {

    lateinit var binding: ActivityCreateAccBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)

        binding = ActivityCreateAccBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()


        val signInLink = findViewById<TextView>(R.id.signIn)

        signInLink.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
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
            val pass =binding.passwordbox.editText.toString()
            val confPass =binding.confpasswordbox.editText.toString()
            val name = fname + " " + lname
            val DOB = DDOB + MDOB + YDOB

          //  if (fname.isNotEmpty() && lname.isNotEmpty() && DDOB.isNotEmpty() && MDOB.isNotEmpty() && YDOB.isNotEmpty() && gender.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty())
            if ( email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty())
            {
                if (pass.equals(confPass))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful)
                        {
                            val intent = Intent(this , MainActivity::class.java)
                            startActivity(intent)

                        }
                        else{
                            Toast.makeText(this,it.exception.toString() ,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "check all", Toast.LENGTH_SHORT).show()
            }


        }
    }
}
