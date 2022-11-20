package com.example.hatrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class facilityy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facilityy)
        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipN7cFaNksqzFRs9upu-cEycW9Uft3uV0cUg1Lf8=w750-h606-p-k-no"))
        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipPJ6jAMFUmfeRK8a4K_YwgvsYI_l-CPiJwACQdU=w750-h401-p-k-no"))
        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipPQsoHN47xpQojOsHMwdJGuXh-UQsNOy78pH4Fm=w750-h813-p-k-no"))
        imageList.add(SlideModel("https://lh5.googleusercontent.com/p/AF1QipMNGdwqd9f8aIQUiAxKgazap2qL462bXL_k5Hm9=w750-h813-p-k-no"))

        imageSlider.setImageList(imageList, ScaleTypes.FIT)

    }
}