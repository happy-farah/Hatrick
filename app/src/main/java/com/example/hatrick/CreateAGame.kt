package com.example.hatrick

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
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
import kotlin.collections.ArrayList

class CreateAGame : AppCompatActivity() , DatePickerDialog.OnDateSetListener {
    private val calendar = Calendar.getInstance()
    private var stime = 0
    private var ftime = 0
    private lateinit var finishTime: String
    private lateinit var database: DatabaseReference
    private val Dformatter = SimpleDateFormat("dd")
    private val Mformatter = SimpleDateFormat("MM")
    private val Yformatter = SimpleDateFormat("yyyy")
    @SuppressLint("MissingInflatedId", "SetTextI18n")
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
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                val AM_PM: String
                AM_PM = if (hourOfDay < 12) {
                    "AM"
                } else {
                    "PM"
                }
                findViewById<EditText>(R.id.time).setText(String.format("$hourOfDay $AM_PM"))
                stime = hourOfDay
            }
        }, hour, minute, false)
        findViewById<EditText>(R.id.time).setOnClickListener{
            mTimePicker.show()
        }
        var nohours = 0
        val hvalue = findViewById<TextView>(R.id.value)
        val hplusBtn = findViewById<TextView>(R.id.plusBtn)
        val hminusBtn = findViewById<TextView>(R.id.minusBtn)
        hvalue.text = "" + nohours
        hplusBtn.setOnClickListener {
            hvalue.text = "" + ++nohours
        }
        hminusBtn.setOnClickListener {
            if (nohours > 0 ) {
                hvalue.text = "" + --nohours
            }
        }

        val fieldInfoIntent = intent
        val fieldID = fieldInfoIntent.getStringExtra("fieldID").toString()
        val ownerId = fieldInfoIntent.getStringExtra("ownerId")
        val fieldName = fieldInfoIntent.getStringExtra("fieldName")
        val sportType = fieldInfoIntent.getStringExtra("sportType")
        val pricePerPerson = findViewById<TextView>(R.id.pricePP)
        val price = findViewById<TextView>(R.id.priceR)
        val fieldData = FirebaseFirestore.getInstance()
        var tPrice = 0F
        var capacity = 0
        var openingtime = ""
        fieldData.collection("Fields").whereEqualTo("fieldID", fieldID)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        val spricePerPerson = doc.data.getValue("pricePerPerson").toString()
                        pricePerPerson.setText(spricePerPerson as CharSequence?)
                        val rPrice = doc.data.getValue("wholePrice").toString()
                        price.setText(rPrice as CharSequence?)
                        tPrice = price.text.toString().toFloat()
                        capacity = doc.data.getValue("capacity").toString().toInt()
                        openingtime = doc.data.getValue("openingTimes").toString()
                    }
                }
            }
        var myPlayers = 0
        val value = findViewById<TextView>(R.id.valuemy)
        val plusBtn = findViewById<TextView>(R.id.plusBtnmy)
        val minusBtn = findViewById<TextView>(R.id.minusBtnmy)
        value.setText("" + myPlayers)
        val pvalue2 = findViewById<TextView>(R.id.valuemin)
        plusBtn.setOnClickListener {
            if (myPlayers < capacity ){
            value.setText("" + ++myPlayers)
            pvalue2.setText("" + myPlayers)
            }
        }

        minusBtn.setOnClickListener {
            if (myPlayers > 0 )
            {
            value.setText("" + --myPlayers)
            pvalue2.setText("" + myPlayers)
            }
        }

        var minNOPlayers = myPlayers

        val hplusBtn2 = findViewById<TextView>(R.id.plusBtnmin)
        val hminusBtn2 = findViewById<TextView>(R.id.minusBtnmin)
        pvalue2.text = minNOPlayers.toString()
        minNOPlayers = myPlayers
        hplusBtn2.setOnClickListener {
            if (minNOPlayers < capacity )
            {
            pvalue2.text = "" + ++minNOPlayers
            }
        }
        hminusBtn2.setOnClickListener {
            if (minNOPlayers > 0 )
            {
                pvalue2.text = "" + --minNOPlayers
            }
        }

        val check = findViewById<Button>(R.id.checktime)
        check.setOnClickListener {
            val y = findViewById<EditText>(R.id.yearBox).text.toString().toInt()
            val m = findViewById<EditText>(R.id.monBox).text.toString().toInt()
            val d = findViewById<EditText>(R.id.dayBox).text.toString().toInt()
            val checked= datevalidation(d, m, y)
            if (checked==1) {
                var resercheck = checkReservations(fieldID, openingtime, hvalue.text.toString().toInt())
                if (resercheck == 1) {
                    Toast.makeText(
                        this,
                        "Not Reserved",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (resercheck == 0){
                    Toast.makeText(
                        this,
                        "Already Reserved",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if (resercheck == 2){
                    Toast.makeText(
                        this,
                        "Field opening time $openingtime",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    resercheck = checkReservations(fieldID, openingtime, hvalue.text.toString().toInt())
                    if (resercheck == 1) {
                        Toast.makeText(
                            this,
                            "Not Reserved",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (resercheck == 0){
                        Toast.makeText(
                            this,
                            "Already Reserved",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else if (resercheck == 2){
                        Toast.makeText(
                            this,
                            "Field opening time $openingtime",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else {
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show()
        }
        }
        val public = findViewById<Switch>(R.id.publicity)
        var publicity = "false"
        val forPublic = findViewById<LinearLayout>(R.id.forPublic)
        val reservPrice = findViewById<LinearLayout>(R.id.reservPrice)
        public?.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                publicity = "true"
                forPublic.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                reservPrice.layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
            } else {
                publicity = "false"
                forPublic.layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                reservPrice.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
        })

        val checktym = findViewById<Button>(R.id.checktime)
        val calcP = findViewById<Button>(R.id.calcPriceBtn)

        val createBtn = findViewById<Button>(R.id.createGameBtn)
        if (sportType == "Football") {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            hplusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            hminusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            checktym.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            calcP.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
        }
        if (sportType == "Basketball") {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            hplusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            hminusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            checktym.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            calcP.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (sportType == "Tennis") {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            hplusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            hminusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            checktym.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            calcP.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (sportType == "Handball") {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            hplusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            hminusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            checktym.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            calcP.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (sportType == "Badminton") {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            hplusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            hminusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            checktym.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            calcP.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (sportType == "Volleyball") {
            createBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            plusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            minusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            hplusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            hminusBtn2.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            hplusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            hminusBtn.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            checktym.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            calcP.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
        }

        val create = findViewById<Button>(R.id.createGameBtn)
        create.setOnClickListener {
            val year = findViewById<EditText>(R.id.yearBox).text.toString()
            val month = findViewById<EditText>(R.id.monBox).text.toString()
            val day = findViewById<EditText>(R.id.dayBox).text.toString()
            val y = findViewById<EditText>(R.id.yearBox).text.toString().toInt()
            val m = findViewById<EditText>(R.id.monBox).text.toString().toInt()
            val d = findViewById<EditText>(R.id.dayBox).text.toString().toInt()
            val startTime = findViewById<EditText>(R.id.time).text.toString()
            val reserveDate = "$day/$month/$year"
            if(day.isNotEmpty() && month.isNotEmpty() && year.isNotEmpty() && startTime.isNotEmpty() && hvalue.text.toString() != "0"){
                if(capacity >= pvalue2.text.toString().toInt()){
                    val checked= datevalidation(d, m, y)
                    if (checked==1) {
                        val Timearray = arrayListOf<Int>()
                        for (i in stime..(ftime-1)) {
                            Timearray.add(i)
                        }
                        var resercheck = checkReservations(fieldID,openingtime,hvalue.text.toString().toInt())
                        if (resercheck == 1) {
                            createGame(
                                fieldID,
                                ownerId,
                                fieldName,
                                sportType,
                                hvalue.text.toString().toInt(),
                                pvalue2.text.toString().toInt(),
                                reserveDate,
                                startTime,
                                finishTime,
                                pricePerPerson.text.toString().toFloat(),
                                Timearray,
                                publicity,
                                value.text.toString().toInt(),
                                tPrice
                            )
                        } else if (resercheck == 2){
                            Toast.makeText(
                                this,
                                "Field opening time $openingtime",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else if (resercheck == 0){
                            Toast.makeText(
                                this,
                                "Already Reserved",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            resercheck = checkReservations(fieldID,openingtime,hvalue.text.toString().toInt())
                            if (resercheck == 1) {
                                createGame(
                                    fieldID,
                                    ownerId,
                                    fieldName,
                                    sportType,
                                    hvalue.text.toString().toInt(),
                                    pvalue2.text.toString().toInt(),
                                    reserveDate,
                                    startTime,
                                    finishTime,
                                    pricePerPerson.text.toString().toFloat(),
                                    Timearray,
                                    publicity,
                                    value.text.toString().toInt(),
                                    tPrice
                                )
                            } else if (resercheck == 2){
                                Toast.makeText(
                                    this,
                                    "Field opening time $openingtime",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else if (resercheck == 0){
                                Toast.makeText(
                                    this,
                                    "Already Reserved",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(this, "Minimum number of players exceeds the capacity", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Please fill all the information", Toast.LENGTH_SHORT).show()
            }
        }
        val calcp =findViewById<Button>(R.id.calcPriceBtn)
        calcp.setOnClickListener {
            var TP :Float = 0F
            if (publicity == "true") {
                TP = (((pricePerPerson.text.toString().toFloat()) * (value.text.toString().toInt())) * (hvalue.text.toString().toInt()))
            }
            else if (publicity=="false")
            {
                TP =  (tPrice * (hvalue.text.toString().toInt()))
            }
            Toast.makeText(
                this,
                "The total price is $TP JOD",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun datevalidation(day:Int? = null,month:Int? = null,year:Int? = null):Int {
        var timeflag = 0
        val CurrentYear = Calendar.getInstance().get(Calendar.YEAR).toString().toInt()
        val Currentmonth = Calendar.getInstance().get(Calendar.MONTH).toString().toInt() + 1
        val Currentday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString().toInt()
        if (CurrentYear==year)
        {
            if(Currentmonth==month)
            {
                if (day != null) {
                    if(Currentday <= day.toInt()) {
                        timeflag = 1
                    }
                }
            }
            else if (month != null) {
                if (Currentmonth < month.toInt()) {
                    timeflag = 1
                }
            }

        }
        else if (year != null) {
            if (CurrentYear < year.toInt()) {
                timeflag = 1
            }
            else{
                timeflag = 0
            }
        }
        return timeflag
    }
    var reservationflag: Int = -1
    @SuppressLint("SuspiciousIndentation")
    private fun checkReservations(fieldID:String, openingtime:String, nohours : Int):Int {
        val year = findViewById<EditText>(R.id.yearBox).text.toString()
        val month = findViewById<EditText>(R.id.monBox).text.toString()
        val day = findViewById<EditText>(R.id.dayBox).text.toString()
        val reserveDate = "$day/$month/$year"
        ftime = stime + nohours
        if(ftime > 24){
            ftime = nohours
        }
        if (ftime < 12) {
            finishTime = "$ftime AM"
        } else {
            finishTime = "$ftime PM"
        }
        val Timearray = arrayListOf<Int>()
        for (i in stime..(ftime-1)) {
            Timearray.add(i)
        }
        val Reservations = FirebaseFirestore.getInstance()
                    Reservations.collection("Reservations").whereEqualTo("fieldID", fieldID).whereEqualTo("reservationDate", reserveDate).whereArrayContainsAny("timearray", Timearray)
                        .get().addOnCompleteListener {
                            if (it.isSuccessful) {
                                val r = it.result
                                if (r != null) {
                                    if (!(r.isEmpty)) {
                                        reservationflag = 0
                                    } else {
                                        reservationflag = 1
                                    }
                                }
                            }
                        }
        val op = openingtime.substringBefore("-")
        var open = op.substringBefore(":")
        open = open.replace(" ","")
        val cs = openingtime.substringAfter("-")
        var close = cs.substringBefore(":")
        close = close.replace(" ","")
        if(stime < (open.toInt())) {
            reservationflag = 2
        }
        if(ftime < (open.toInt())) {
            reservationflag = 2
        }
        if(stime >= (close.toInt()) ) {
            reservationflag = 2
        }
        if(ftime>=(close.toInt())) {
            reservationflag = 2
        }
        return reservationflag
    }

    private fun createGame(fieldID:String? = null,ownerId:String? = null,fieldName:String? = null,sportType:String? = null,nohours : Int,minNOPlayers : Int? = null,reserveDate:String? = null,startTime:String? = null,finishTime:String? = null, pricePerPerson:Float,Timearray:ArrayList<Int>,publicty:String? = null,myPlayers : Int,fieldprice:Float) {
        var totalPrice :Float = 0F
        if (publicty == "true") {
            totalPrice = ((pricePerPerson * myPlayers) * nohours)
        }
        else if (publicty=="false")
        {
            totalPrice =  (fieldprice* nohours)
        }
        database = FirebaseDatabase.getInstance().getReference("Reservations")
        val game = Reservation(
            null,
            fieldID,
            ownerId,
            getCurrentUserID(),
            publicty,
            fieldName,
            sportType,
            nohours,
            myPlayers,
            minNOPlayers,
            reserveDate,
            startTime,
            finishTime,
            Timearray,
            pricePerPerson,
            totalPrice
        )
        val UserFireData = FirebaseFirestore.getInstance()
        val currnetdoc = UserFireData.collection("Reservations").document()
        currnetdoc.set(
            game,
            SetOptions.merge()
        ).addOnSuccessListener {
            Toast.makeText(
                this,
                "Game created successfully",
                Toast.LENGTH_SHORT
            ).show()
            currnetdoc.update("reservationID",currnetdoc.id)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(
                this,
                "Failed to create game",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        displayFormattedDate(calendar.timeInMillis)
    }
    private fun displayFormattedDate(timestamp: Long) {
        findViewById<EditText>(R.id.dayBox).setText(Dformatter.format(timestamp))
        findViewById<EditText>(R.id.monBox).setText(Mformatter.format(timestamp))
        findViewById<EditText>(R.id.yearBox).setText(Yformatter.format(timestamp))
        Log.i("Formatting", timestamp.toString())
    }
}