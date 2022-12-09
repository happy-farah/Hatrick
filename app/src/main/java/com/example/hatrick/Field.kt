package com.example.hatrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Field : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)
        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipN7cFaNksqzFRs9upu-cEycW9Uft3uV0cUg1Lf8=w750-h606-p-k-no"))
        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipPJ6jAMFUmfeRK8a4K_YwgvsYI_l-CPiJwACQdU=w750-h401-p-k-no"))
        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipPQsoHN47xpQojOsHMwdJGuXh-UQsNOy78pH4Fm=w750-h813-p-k-no"))
        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipMNGdwqd9f8aIQUiAxKgazap2qL462bXL_k5Hm9=w750-h813-p-k-no"))
        imageSlider.setImageList(imageList, ScaleTypes.FIT)


        val UserFireData = FirebaseFirestore.getInstance()
        UserFireData.collection("Fields")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        val fieldName = findViewById<TextView>(R.id.fieldNameTxt)
                        fieldName.setText(doc.data.getValue("fieldName") as CharSequence?)
                        val location = findViewById<TextView>(R.id.location)
                        location.setText(doc.data.getValue("address") as CharSequence?)
                        val cap = findViewById<TextView>(R.id.capacity)
                        cap.setText(doc.data.getValue("capacity") as CharSequence?)
                        val ground = findViewById<TextView>(R.id.groundType)
                        ground.setText(doc.data.getValue("groundType")as CharSequence?)
                    }
                }
            }
    }
}