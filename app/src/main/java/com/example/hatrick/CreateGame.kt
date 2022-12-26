package com.example.hatrick

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.*


class CreateGame : DialogFragment(R.layout.fragment_create_game) {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("MMMM d, yyyy hh:mm:ss a", Locale.US)
    private lateinit var database: DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        var id = 0
        var id2 = 0
        var value = view.findViewById<TextView>(R.id.value1)
        var plusBtn = view.findViewById<TextView>(R.id.plusBtn1)
        var minusBtn = view.findViewById<TextView>(R.id.minusBtn1)
        var value2 = view.findViewById<TextView>(R.id.value2)
        var plusBtn2 = view.findViewById<TextView>(R.id.plusBtn2)
        var minusBtn2 = view.findViewById<TextView>(R.id.minusBtn2)

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

        val public = view.findViewById<Switch>(R.id.publicity)
        if (public != null) {
            //public.setOnCheckedChangeListener(context);
        }


//        view.findViewById<TextView>(R.id.textView).setOnClickListener {
//            DatePickerDialog(
//                this,
//                this,
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)
//            ).show()
//        }
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        calendar.set(year, month, dayOfMonth)
//        displayFormattedDate(calendar.timeInMillis)
//        TimePickerDialog(
//            this,
//            this,
//            calendar.get(Calendar.HOUR_OF_DAY),
//            calendar.get(Calendar.MINUTE),
//            false
//        ).show()
//    }
//
//    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        calendar.apply {
//            set(Calendar.HOUR_OF_DAY, hourOfDay)
//            set(Calendar.MINUTE, minute)
//        }
//        displayFormattedDate(calendar.timeInMillis)
//    }
//
//    private fun displayFormattedDate(timestamp: Long) {
//        findViewById<TextView>(R.id.textView).text = formatter.format(timestamp)
//        Log.i("Formatting", timestamp.toString())
//    }


    }
}