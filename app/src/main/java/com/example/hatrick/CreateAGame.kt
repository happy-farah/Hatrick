package com.example.hatrick

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.*

class CreateAGame : AppCompatActivity() , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("hh:mm:ss a", Locale.US)
    private lateinit var database: DatabaseReference
    private val Dformatter = SimpleDateFormat("d")
    private val Mformatter = SimpleDateFormat("MM")
    private val Yformatter = SimpleDateFormat("yyyy")


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_agame)

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

        val public = findViewById<Switch>(R.id.publicity)
        var pulicity="false"

        public?.setOnCheckedChangeListener({ _ , isChecked ->
             if (isChecked) {
                pulicity="true"
            } else {
                pulicity="false"
            }

    })


        var id = 0
        var id2 = 0
        var value = findViewById<TextView>(R.id.value1)
        var plusBtn = findViewById<TextView>(R.id.plusBtn1)
        var minusBtn = findViewById<TextView>(R.id.minusBtn1)
        var value2 = findViewById<TextView>(R.id.value2)
        var plusBtn2 = findViewById<TextView>(R.id.plusBtn2)
        var minusBtn2 = findViewById<TextView>(R.id.minusBtn2)

        value.setText("" + id)

        plusBtn.setOnClickListener {
            value.setText("" + ++id)
        }

        minusBtn.setOnClickListener {
            value.setText("" + --id)
        }
        value2.setText("" + id2)

        plusBtn2.setOnClickListener {
            value2.setText("" + ++id2)
        }

        minusBtn2.setOnClickListener {
            value2.setText("" + --id2)
        }
        val FieldInfoIntent = intent
        val fieldID = FieldInfoIntent.getStringExtra("fieldID")
        val wholePrice = findViewById<TextView>(R.id.priceR)
        val pricePerPerson = findViewById<TextView>(R.id.pricePP)
        val FieldData = FirebaseFirestore.getInstance()
        FieldData.collection("Fields").whereEqualTo("fieldID", fieldID)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {

                        val swholePrice = doc.data.getValue("wholePrice").toString()
                        wholePrice.setText(swholePrice as CharSequence?)
                        val spricePerPerson = doc.data.getValue("pricePerPerson").toString()
                        pricePerPerson.setText(spricePerPerson as CharSequence?)
                    }
                }
            }
        val create = findViewById<Button>(R.id.createGameBtn)
        create.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("Reservations")
            val game = Resevation(
                fieldID,
                getCurrentUserID(),
                pulicity,
                value.text.toString().toInt(),
                value2.text.toString().toInt(),
                null,
                null,
                wholePrice.text.toString().toFloat()
            )
            val UserFireData = FirebaseFirestore.getInstance()
            UserFireData.collection("Reservations")
                .document()
                .set(
                    game,
                    SetOptions.merge()
                ).addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Failed to save information",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)
        TimePickerDialog(
            this,
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()

    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        displayFormattedDate(calendar.timeInMillis)
    }

    private fun displayFormattedDate(timestamp: Long) {
        findViewById<TextView>(R.id.dayBox).text = Dformatter.format(timestamp)
        findViewById<TextView>(R.id.monBox).text = Mformatter.format(timestamp)
        findViewById<TextView>(R.id.yearBox).text = Yformatter.format(timestamp)
        Log.i("Formatting", timestamp.toString())
        findViewById<TextView>(R.id.textView).text = formatter.format(timestamp)
        Log.i("Formatting", timestamp.toString())
    }

}




//        val test = findViewById<TextView>(R.id.test)
//        test.setOnClickListener {
//            Toast.makeText(this, "$game", Toast.LENGTH_SHORT).show()
//
//        }