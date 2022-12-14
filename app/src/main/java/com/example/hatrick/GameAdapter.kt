package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GameAdapter(var c: Context, private val gameList : ArrayList<Reservation>, private val card: String) : RecyclerView.Adapter<GameAdapter.MyViewHolder>() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item,
            parent,false)
        val viewField = itemView.findViewById<TextView>(R.id.viewField)
        val joinGame = itemView.findViewById<TextView>(R.id.joinGame)
        if (card=="Football")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
        }
        if (card=="Basketball")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (card=="Tennis")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (card=="Handball")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (card=="Badminton")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (card=="Volleyball")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
        }

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val game : Reservation = gameList[position]
        holder.fieldName.text = game.fieldName
        holder.time.text = game.gameTime
        holder.date.text = game.gameDate
        holder.pricePP.text = game.pricePerPerson.toString()
        holder.noPlayers.text = game.noplayers.toString()
        val fieldId = game.fieldID
        holder.viewField.setOnClickListener {
            val intent = Intent(c, FieldInfo2::class.java)
            intent.putExtra("fieldID", fieldId)
            c.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return gameList.size
    }
    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val fieldName: TextView = itemView.findViewById(R.id.FieldName)
        val noPlayers : TextView = itemView.findViewById(R.id.numberOfplayers)
        val date : TextView = itemView.findViewById(R.id.date)
        val time : TextView = itemView.findViewById(R.id.time)
        val pricePP : TextView = itemView.findViewById(R.id.pricePP)
        val viewField : Button = itemView.findViewById(R.id.viewField)


    }
}

