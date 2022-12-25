package com.example.hatrick

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MyAdapter(var c: Context, private val FieldList : ArrayList<Field>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.field_item,
            parent,false)
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
            val intent = Intent(c, FieldInfo::class.java)
            intent.putExtra("fieldName",fieldName)
            intent.putExtra("phoneNumber",phone)
            intent.putExtra("email",email)
            intent.putExtra("address",location)
            intent.putExtra("length",length)
            intent.putExtra("width",width)
            intent.putExtra("capacity",capacity)
            intent.putExtra("groundType",groundtype)
            intent.putExtra("services",services)
            intent.putExtra("image", image)
            c.startActivity(intent)
        }

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

//package com.example.hatrick
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class MyAdapter(private val FieldList : ArrayList<Field>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//
//    private  lateinit var mListener : onItemClickListener
//
//    interface onItemClickListener {
//
//        fun onItemClick(position: Int)
//
//    }
//
//    fun setOnItemClickListener (listener: onItemClickListener){
//        mListener = listener
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.field_item,
//            parent,false)
//        return MyViewHolder(itemView,mListener)
//
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
//        val field : Field = FieldList[position]
//
//        holder.fieldName.text = field.fieldName
//        holder.address.text = field.address
//
//    }
//
//    override fun getItemCount(): Int {
//
//        return FieldList.size
//    }
//
//
//    public class MyViewHolder(itemView : View , listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
//
//        val fieldName : TextView = itemView.findViewById(R.id.FieldName)
//        val address : TextView = itemView.findViewById(R.id.FieldLocation)
//
//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
//
//    }
//
//}