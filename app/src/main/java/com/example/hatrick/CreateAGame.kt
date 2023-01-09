package com.example.hatrick

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.*

class CreateAGame : AppCompatActivity() , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("hh", Locale.US)
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
        var publicity="false"
        val forPublic = findViewById<LinearLayout>(R.id.forPublic)
        val reservPrice = findViewById<LinearLayout>(R.id.reservPrice)
//        forPublic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
//        reservPrice.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)


        public?.setOnCheckedChangeListener({ _ , isChecked ->
             if (isChecked) {
                 publicity="true"

                 forPublic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                 reservPrice.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)


             } else {
                publicity="false"
//                 forPublic.visibility = View.INVISIBLE
                 forPublic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                 reservPrice.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            }

    })




        var id = 0
        var id2 = 0
        var nhours = 0
        val value = findViewById<TextView>(R.id.value1)
        val plusBtn = findViewById<TextView>(R.id.plusBtn1)
        val minusBtn = findViewById<TextView>(R.id.minusBtn1)
        val value2 = findViewById<TextView>(R.id.value2)
        val plusBtn2 = findViewById<TextView>(R.id.plusBtn2)
        val minusBtn2 = findViewById<TextView>(R.id.minusBtn2)
        val hvalue = findViewById<TextView>(R.id.value)
        val hplusBtn = findViewById<TextView>(R.id.plusBtn)
        val hminusBtn = findViewById<TextView>(R.id.minusBtn)
        hvalue.setText("" + nhours)

        hplusBtn.setOnClickListener {
            hvalue.setText("" + ++nhours)
        }

        hminusBtn.setOnClickListener {
            hvalue.setText("" + --nhours)
        }

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
        val fieldID = FieldInfoIntent.getStringExtra("fieldID").toString()
        val fieldName = FieldInfoIntent.getStringExtra("fieldName")
        val sportType = FieldInfoIntent.getStringExtra("sportType")
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
        val createBtn = findViewById<Button>(R.id.createGameBtn)
//        val plusButn = findViewById<Button>(R.id.plusBtn)
//        val minusButn = findViewById<Button>(R.id.minusBtn)
//        val plusButn1 = findViewById<Button>(R.id.plusBtn1)
//        val minusButn1 = findViewById<Button>(R.id.minusBtn1)
//        val plusButn2 = findViewById<Button>(R.id.plusBtn2)
//        val minusButn2 = findViewById<Button>(R.id.minusBtn2)

        if (sportType=="Football")
        {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            plusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            minusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
        }
        if (sportType=="Basketball")
        {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            plusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            minusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (sportType=="Tennis")
        {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            plusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            minusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (sportType=="Handball")
        {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            plusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            minusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (sportType=="Badminton")
        {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            plusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            minusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (sportType=="Volleyball")
        {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            plusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            minusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
        }

        val year =findViewById<EditText>(R.id.yearBox).text.toString()
        val month =findViewById<EditText>(R.id.monBox).text.toString()
        val day =findViewById<EditText>(R.id.dayBox).text.toString()
        val time =findViewById<EditText>(R.id.time).text.toString()
        val reservDate = day +"/"+ month +"/"+ year
        val CurrentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val Currentmonth = Calendar.getInstance().get(Calendar.MONTH).toString()
        val Currentday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()


        findViewById<EditText>(R.id.time).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(mEdit: Editable) {
                val time =findViewById<EditText>(R.id.time).text.toString()
                val warning = findViewById<TextView>(R.id.warning)
                if(checkTime(fieldID ,reservDate, time)==false){
                    warning.setVisibility(View.VISIBLE);
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val time = findViewById<EditText>(R.id.time).text.toString()
                val warning = findViewById<TextView>(R.id.warning)
                if (checkTime(fieldID, reservDate, time)==false) {
                    warning.setVisibility(View.VISIBLE);
                }
            }

                     })

        val create = findViewById<Button>(R.id.createGameBtn)
        create.setOnClickListener {
            val year =findViewById<EditText>(R.id.yearBox).text.toString()
            val month =findViewById<EditText>(R.id.monBox).text.toString()
            val day =findViewById<EditText>(R.id.dayBox).text.toString()
            val time =findViewById<EditText>(R.id.time).text.toString()
            val reservDate = day +"/"+ month +"/"+ year
            val CurrentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
            val Currentmonth = Calendar.getInstance().get(Calendar.MONTH).toString()
            val Currentday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
            val Currentdate = Calendar.getInstance().get(Calendar.DATE)

            if (CurrentYear==year)
            {
                if (Currentmonth<month)
                {
                    createGame(fieldID,
                        publicity,
                        fieldName,
                        sportType,
                        hvalue.text.toString().toInt(),
                        value.text.toString().toInt(),
                        value2.text.toString().toInt(),
                        reservDate,
                        time,
                        pricePerPerson.text.toString().toFloat(),
                        wholePrice.text.toString().toFloat())

                }
                if(month==Currentmonth)
                {
                    if(Currentday<=day)
                    {
                        createGame(fieldID,
                            publicity,
                            fieldName,
                            sportType,
                            hvalue.text.toString().toInt(),
                            value.text.toString().toInt(),
                            value2.text.toString().toInt(),
                            reservDate,
                            time,
                            pricePerPerson.text.toString().toFloat(),
                            wholePrice.text.toString().toFloat())
                    }

                }

            }
            else if (CurrentYear<year) {
                createGame(
                    fieldID,
                    publicity,
                    fieldName,
                    sportType,
                    hvalue.text.toString().toInt(),
                    value.text.toString().toInt(),
                    value2.text.toString().toInt(),
                    reservDate,
                    time,
                    wholePrice.text.toString().toFloat()
                )
            }
        }

    }
    fun checkTime (fieldID:String ,reservDate:String, time:String):Boolean {
        var flag : Boolean =true
        val Reservations = FirebaseFirestore.getInstance()
        Reservations.collection("Reservations").whereEqualTo("fieldID", fieldID)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    Reservations.collection("Reservations").whereEqualTo("gameDate", reservDate)
                        .get().addOnCompleteListener {
                            if (it.isSuccessful) {
                                Reservations.collection("Reservations")
                                    .whereEqualTo("gameTime", time)
                                    .get().addOnSuccessListener {
                                        flag = false
                                    }.addOnFailureListener {
                                        flag = true
                                    }
                            }

                        }
                }
            }

        return flag
    }

    private fun createGame(fieldID:String? = null,publicity:String? = null,fieldName:String? = null,sportType:String? = null,hvalue : Int? = null,value : Int? = null, value2 : Int? = null, reservDate:String? = null, time:String? = null, pricePerPerson:Float? = null, wholePrice:Float? = null) {
        database = FirebaseDatabase.getInstance().getReference("Reservations")
        val game = Reservation(
            fieldID,
            getCurrentUserID(),
            publicity,
            fieldName,
            sportType,
            hvalue,
            value,
            value2,
            reservDate,
            time,
            wholePrice
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
        findViewById<EditText>(R.id.dayBox).setText(Dformatter.format(timestamp))
        findViewById<EditText>(R.id.monBox).setText(Mformatter.format(timestamp))
        findViewById<EditText>(R.id.yearBox).setText(Yformatter.format(timestamp))
        findViewById<EditText>(R.id.time).setText(formatter.format(timestamp))
        Log.i("Formatting", timestamp.toString())
    }


}




//        val test = findViewById<TextView>(R.id.test)
//        test.setOnClickListener {
//            Toast.makeText(this, "$game", Toast.LENGTH_SHORT).show()
//
//        }