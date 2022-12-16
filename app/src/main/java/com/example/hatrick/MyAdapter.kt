package com.example.hatrick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class MyAdapter(private val FieldList : ArrayList<Field>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.field_item,
        parent,false)
//        val rate : ImageView = itemView.findViewById(R.id.fieldRateImg)
//        rate.setOnClickListener {
//            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT)
//                .show()
//        }
        return MyViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val field : Field = FieldList[position]

        holder.fieldName.text = field.fieldName
        holder.address.text = field.address


    }

    override fun getItemCount(): Int {

       return FieldList.size
    }


    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val fieldName : TextView = itemView.findViewById(R.id.FieldName)
        val address : TextView = itemView.findViewById(R.id.FieldLocation)


    }


}


