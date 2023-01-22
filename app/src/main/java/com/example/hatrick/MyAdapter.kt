package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.squareup.picasso.Picasso

class MyAdapter(var c: Context, private var FieldList : ArrayList<Field>, private val card: String) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    @SuppressLint("MissingInflatedId")
    private lateinit var database: DatabaseReference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.field_item,
            parent,false)
        val fieldname = itemView.findViewById<TextView>(R.id.FieldName)
        if (card=="Football")
        {
            fieldname.setTextColor(android.graphics.Color.parseColor("#009900"))
        }
        if (card=="Basketball")
        {
            fieldname.setTextColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (card=="Tennis")
        {
            fieldname.setTextColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (card=="Handball")
        {
            fieldname.setTextColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (card=="Badminton")
        {
            fieldname.setTextColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (card=="Volleyball")
        {
            fieldname.setTextColor(android.graphics.Color.parseColor("#4361ee"))
        }
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val field : Field = FieldList[position]
        holder.fieldName.text = field.fieldName
        holder.address.text = field.address
        val image = field.image
        val uri = image?.toUri()
        Picasso.get().load(uri).into(holder.image)
        holder.itemView.rootView.setOnClickListener {
            val fieldName = field.fieldName
            val phone = field.phoneNumber
            val email = field.email
            val location = field.address
            val length = field.length
            val width = field.width
            val capacity = field.capacity
            val groundtype = field.groundType
            val services = field.services
            val image = field.image
            val fieldId=field.fieldID
            val pricePerPerson = field.pricePerPerson
            val wholePrice = field.wholePrice
            val openingtime = field.openingTimes
            val ownerId = field.ownerID
            val intent = Intent(c, FieldInfo::class.java)
            intent.putExtra("fieldName",fieldName)
            intent.putExtra("phoneNumber",phone)
            intent.putExtra("email",email)
            intent.putExtra("address",location)
            intent.putExtra("length",length)
            intent.putExtra("width",width)
            intent.putExtra("capacity",capacity)
            intent.putExtra("wholePrice",wholePrice)
            intent.putExtra("pricePerPerson",pricePerPerson)
            intent.putExtra("groundType",groundtype)
            intent.putExtra("services",services)
            intent.putExtra("image", image)
            intent.putExtra("fieldID",fieldId)
            intent.putExtra("openingTimes", openingtime)
            intent.putExtra("ownerId", ownerId)
            intent.putExtra("sportType",card)
            c.startActivity(intent)
        }
    }
    fun setFilteredList(FieldList : ArrayList<Field>){
        this.FieldList = FieldList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return FieldList.size
    }
    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val fieldName : TextView = itemView.findViewById(R.id.FieldName)
        val address : TextView = itemView.findViewById(R.id.FieldLocation)
        val image: ImageView = itemView.findViewById(R.id.fieldImage)
    }
}