package com.example.hatrick

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hatrick.databinding.ActivityCreateAccBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.*


class CreateAcc : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityCreateAccBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val calendar = Calendar.getInstance()
    private val Dformatter = SimpleDateFormat("d")
    private val Mformatter = SimpleDateFormat("MM")
    private val Yformatter = SimpleDateFormat("yyyy")
    private lateinit var selectedgender: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)

        binding = ActivityCreateAccBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        findViewById<EditText>(R.id.dayBox).setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        findViewById<EditText>(R.id.monBox).setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        findViewById<EditText>(R.id.yearBox).setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        var genderr = findViewById<RadioGroup>(R.id.gender)

        genderr.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                selectedgender = genderr.checkedRadioButtonId.toString()
                if (selectedgender == "2131231062")
                {
                    selectedgender="Male"
                }
                else
                {
                    selectedgender="Female"
                }
            })

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

        findViewById<EditText>(R.id.phoneBox).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {
                val phone =binding.phoneBox.text.toString()
                val warning = binding.warning
                if(phone.isEmpty()){
                    warning.setVisibility(View.INVISIBLE);
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val codephone = binding.phoneBox.text.take(3).toString()
                val phone =binding.phoneBox.text.toString()
                val warning = binding.warning
                if (!codephone.equals("079") && !codephone.equals("078") && !codephone.equals("077")) {
                    warning.setVisibility(View.VISIBLE);
                }else {
                    if(phone.length == 10) {
                        warning.setVisibility(View.INVISIBLE);
                    }
                }
                if (phone.length != 10) {
                    warning.setVisibility(View.VISIBLE);
                }
            }
        })


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
            val gender = selectedgender
            val phone =binding.phoneBox.text.toString()
            val email =binding.emailBox.text.toString()
            val password =binding.passwordbox.text.toString()
            val confPass =binding.confpasswordbox.text.toString()
            val DOB = DDOB +"/"+ MDOB +"/"+ YDOB
            val codephone = binding.phoneBox.text.take(3).toString()
            val CurrentYear = Calendar.getInstance().get(Calendar.YEAR)

            if (fname.isNotEmpty() && lname.isNotEmpty() && DDOB.isNotEmpty() && MDOB.isNotEmpty() && YDOB.isNotEmpty() && gender.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confPass.isNotEmpty()) {
                if(YDOB < (CurrentYear-5).toString()) {
                    if (phone.length == 10) {
                        if (codephone.equals("079") || codephone.equals("078") || codephone.equals("077")) {
                            if (ValidatePassword()) {
                                if (password.equals(confPass)) {
                                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                database =
                                                    FirebaseDatabase.getInstance()
                                                        .getReference("Users")
                                                val User = User(
                                                    getCurrentUserID(),
                                                    fname,
                                                    lname,
                                                    DOB,
                                                    gender,
                                                    phone,
                                                    email
                                                )
                                                val UserFireData = FirebaseFirestore.getInstance()
                                                UserFireData.collection("users")
                                                    .document(getCurrentUserID())
                                                    .set(
                                                        User,
                                                        SetOptions.merge()
                                                    ).addOnSuccessListener {
                                                        binding.firstNameBox.text.clear()
                                                        binding.lastNameBox.text.clear()
                                                        binding.dayBox.text.clear()
                                                        binding.monBox.text.clear()
                                                        binding.yearBox.text.clear()
                                                        binding.gender.clearCheck()
                                                        binding.phoneBox.text.clear()
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
                        } else {
                            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "You are under the valid age", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)

    }
    private fun displayFormattedDate(timestamp: Long) {
        findViewById<TextView>(R.id.dayBox).text = Dformatter.format(timestamp)
        findViewById<TextView>(R.id.monBox).text = Mformatter.format(timestamp)
        findViewById<TextView>(R.id.yearBox).text = Yformatter.format(timestamp)
        Log.i("Formatting", timestamp.toString())
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

fun getCurrentUserID(): String {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var currentUserID = ""
    if (currentUser != null) {
        currentUser.uid.also { currentUserID = it }
    }
    return currentUserID
}