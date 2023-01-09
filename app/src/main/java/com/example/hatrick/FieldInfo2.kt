package com.example.hatrick
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class FieldInfo2 : AppCompatActivity() {
    private var imageUri: Uri? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_info2)
        val FieldIntent = intent
        val FieldData = FirebaseFirestore.getInstance()
        val fieldId = FieldIntent.getStringExtra("fieldID")
        FieldData.collection("Fields").whereEqualTo("fieldID",fieldId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        val image = doc.data.getValue("image").toString()
                        val uri = image?.toUri()
                        Picasso.get().load(uri).into(findViewById<ImageView>(R.id.img))
                        findViewById<TextView>(R.id.fieldNameTxt).setText(doc.data.getValue("fieldName") as CharSequence?)
                        findViewById<TextView>(R.id.phoneNumber).setText(doc.data.getValue("phoneNumber") as CharSequence?)
                        findViewById<TextView>(R.id.email).setText(doc.data.getValue("email") as CharSequence?)
                        findViewById<TextView>(R.id.location).setText(doc.data.getValue("address") as CharSequence?)
                        val length =doc.data.getValue("length").toString()
                        findViewById<TextView>(R.id.length).setText(length as CharSequence?)
                        val width = doc.data.getValue("width").toString()
                        findViewById<TextView>(R.id.width).setText(width as CharSequence?)
                        val pricePerPerson = doc.data.getValue("pricePerPerson").toString()
                        findViewById<TextView>(R.id.pricePerPerson).setText(pricePerPerson as CharSequence?)
                        findViewById<TextView>(R.id.groundType).setText(doc.data.getValue("groundType")as CharSequence?)
                        findViewById<TextView>(R.id.services).setText(doc.data.getValue("services")as CharSequence?)
                        findViewById<TextView>(R.id.openeingTime).setText(doc.data.getValue("openingTimes")as CharSequence?)
                    }
                }
            }
    }
}