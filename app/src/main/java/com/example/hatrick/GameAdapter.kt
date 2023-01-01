package com.example.hatrick

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class GameAdapter(var c: Context, private val gameList : ArrayList<Reservation>, private val card: String) : RecyclerView.Adapter<GameAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item,
            parent,false)
        val viewField = itemView.findViewById<TextView>(R.id.viewField)
        val joinGame = itemView.findViewById<TextView>(R.id.joinGame)

        if (card=="Football")
        {
            viewField.setTextColor(android.graphics.Color.parseColor("#009900"))
            joinGame.setTextColor(android.graphics.Color.parseColor("#009900"))
        }
        if (card=="Basketball")
        {
            viewField.setTextColor(android.graphics.Color.parseColor("#FF5207"))
            joinGame.setTextColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (card=="Tennis")
        {
            viewField.setTextColor(android.graphics.Color.parseColor("#AAEE00"))
            joinGame.setTextColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (card=="Handball")
        {
            viewField.setTextColor(android.graphics.Color.parseColor("#023e7d"))
            joinGame.setTextColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (card=="Badminton")
        {
            viewField.setTextColor(android.graphics.Color.parseColor("#ae2012"))
            joinGame.setTextColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (card=="Volleyball")
        {
            viewField.setTextColor(android.graphics.Color.parseColor("#4361ee"))
            joinGame.setTextColor(android.graphics.Color.parseColor("#4361ee"))
        }

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val game : Reservation = gameList[position]


        holder.fieldName.text = game.fieldName
        holder.noPlayers.text = game.noplayers.toString()
        holder.viewField.setOnClickListener {
            var phone:String = " "
            var email:String = " "
            var nlocation:String = " "
            var slength:String = " "
            var swidth:String = " "
            var scapacity:String = " "
            var ground:String = " "
            var services:String = " "

            val fieldName = game.fieldName
            val userId = game.userID
            val public = game.public
            val sportType = game.sportType
            val noHours = game.nohours
            val noPlayers = game.noplayers
            val minNoPlayers = game.minNOPlayers
            val gameDate = game.gameDate
            val gameTime = game.gameTime
            val price = game.totalPrice
            val fieldId=game.fieldID
            val intent = Intent(c, FieldInfo::class.java)
            intent.putExtra("fieldID",fieldId)
            val FieldData = FirebaseFirestore.getInstance()
            FieldData.collection("Fields").whereEqualTo("fieldID", fieldId)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (doc in it.result!!) {

                            phone= (doc.data.getValue("phoneNumber") as CharSequence).toString()
                            email = (doc.data.getValue("email") as CharSequence?).toString()
                            nlocation=(doc.data.getValue("address") as CharSequence?).toString()
                            slength = doc.data.getValue("length").toString()
                            swidth = doc.data.getValue("width").toString()
                            scapacity = doc.data.getValue("capacity").toString()
                            ground=(doc.data.getValue("groundType") as CharSequence?).toString()
                            services=(doc.data.getValue("services") as CharSequence?).toString()
                            sportstypes=(doc.data.getValue("sportType") as CharSequence?)
                            val swholePrice = doc.data.getValue("wholePrice").toString()
                            wholePrice.setText(swholePrice as CharSequence?)
                            val spricePerPerson = doc.data.getValue("pricePerPerson").toString()
                            pricePerPerson.setText(spricePerPerson as CharSequence?)
                        }
                    }
                }
            intent.putExtra("fieldName",fieldName)
            intent.putExtra("phoneNumber",phone)
            intent.putExtra("email",email)
            intent.putExtra("address",nlocation)
            intent.putExtra("length",slength)
            intent.putExtra("width",swidth)
            intent.putExtra("capacity",scapacity)
            intent.putExtra("groundType",ground)
            intent.putExtra("services",services)
            intent.putExtra("image", image)
            intent.putExtra("fieldID",fieldId)
            c.startActivity(intent)
        }
//        intent.putExtra("fieldName",fieldName)
//        intent.putExtra( "userId", userId)
//        intent.putExtra( "public", public)
//        intent.putExtra( "sportType" , sportType )
//        intent.putExtra("noHours" , noHours )
//        intent.putExtra("noplayers" , noPlayers)
//        intent.putExtra( "minNoPlayers" , minNoPlayers )
//        intent.putExtra( "gameDate" , gameDate)
//        intent.putExtra( "gameTime" , gameTime )
//        intent.putExtra( "price", price)
    }

    override fun getItemCount(): Int {

        return gameList.size
    }


    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val fieldName : TextView = itemView.findViewById(R.id.FieldName)
        val noPlayers : TextView = itemView.findViewById(R.id.numberOfplayers)
        val viewField : Button = itemView.findViewById(R.id.viewField)

    }

}

