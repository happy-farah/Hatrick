package com.example.hatrick

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class EditProfile : AppCompatActivity(),DatePickerDialog.OnDateSetListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private val calendar = Calendar.getInstance()
    private val Dformatter = SimpleDateFormat("d")
    private val Mformatter = SimpleDateFormat("MM")
    private val Yformatter = SimpleDateFormat("yyyy")
    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        firebaseAuth = FirebaseAuth.getInstance()
        val UserFireData = FirebaseFirestore.getInstance()
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
        val FN = findViewById<EditText>(R.id.firstNameBox)
        val LN = findViewById<EditText>(R.id.lastNameBox)
        val Day = findViewById<EditText>(R.id.dayBox)
        val Year = findViewById<EditText>(R.id.yearBox)
        val Month = findViewById<EditText>(R.id.monBox)
        val Gender = findViewById<RadioGroup>(R.id.gender)
        val PN = findViewById<EditText>(R.id.phoneBox)
        var day = ""
        var year = ""
        var month = ""
        UserFireData.collection("users").whereEqualTo("customerID", getCurrentUserID())
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        FN.setText(doc.data.getValue("firstName") as CharSequence?)
                        LN.setText(doc.data.getValue("lastName") as CharSequence?)
                        val DOB = (doc.data.getValue("dateOfBirth") as CharSequence?).toString()
                        day = DOB.take(2)
                        Day.setText(day)
                        month = DOB.subSequence(3, 5).toString()
                        Month.setText(month)
                        year = DOB.takeLast(4)
                        Year.setText(year)
                        val Gend = (doc.data.getValue("gender") as CharSequence?).toString()
                        if (Gend == "Male") {
                            Gender.check(2131296600)
                        } else if (Gend == "Female") {
                            Gender.check(2131296505)
                        }
                        PN.setText(doc.data.getValue("phoneNumber") as CharSequence?)
                    }
                }
            }
        val Save = findViewById<Button>(R.id.saveBtn)
        Save.setOnClickListener {
            val radioGroup = findViewById<RadioGroup>(R.id.gender)
            val selectedOption: Int = radioGroup.checkedRadioButtonId
            val gender = findViewById<RadioButton>(selectedOption)
            if(Day.text.toString().length<2){
                Day.setText("0"+Day.text)
            }
            if(Month.text.toString().length<2){
                Month.setText("0"+Month.text)
            }
            val newDOB =
                Day.text.toString() + "/" + Month.text.toString() + "/" + Year.text.toString()
            val codephone = findViewById<EditText>(R.id.phoneBox).text.take(3).toString()
            if (PN.length() == 10) {
                if (codephone.equals("079") || codephone.equals("078") || codephone.equals("077")) {
                    UserFireData.collection("users").document(getCurrentUserID())
                .update(
                    "firstName",
                    FN.text.toString(),
                    "lastName",
                    LN.text.toString(),
                    "phoneNumber",
                    PN.text.toString(),
                    "gender",
                    gender,
                    "dateOfBirth",
                    newDOB
                )
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else {
            Toast.makeText(this, "Incorrect Phone Number", Toast.LENGTH_SHORT)
                .show()
        }
    } else {
        Toast.makeText(this, "Incorrect Phone Number", Toast.LENGTH_SHORT).show()
    }

        val changePass = findViewById<Button>(R.id.changePass)
        changePass.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.cancelBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
}