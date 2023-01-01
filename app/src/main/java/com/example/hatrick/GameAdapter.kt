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
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Color
import com.google.type.Color.Builder
import com.squareup.picasso.Picasso


class GameAdapter(var c: Context, private val gameList : ArrayList<Reservation>, private val card: String) : RecyclerView.Adapter<GameAdapter.MyViewHolder>() {


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item,
            parent,false)
        val viewField = itemView.findViewById<TextView>(R.id.viewField)
        val joinGame = itemView.findViewById<TextView>(R.id.joinGame)
        val fieldCard = itemView.findViewById<CardView>(R.id.fCard)

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
        holder.noPlayers.text = game.noplayers.toString()
        holder.viewField.setOnClickListener {
            var phone:String = ""
            var email :String = ""
            var location :String = ""
            var length :String = ""
            var width :String = ""
            var capacity :String = ""
            var groundtype:String = ""
            var services :String = ""
            var image :String = ""
            var pricePerPerson :String = ""
            var wholePrice :String = ""
            //var openingtime :String = ""


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


                            phone = doc.data.getValue("phoneNumber").toString()
                            Toast.makeText(c, "$phone", Toast.LENGTH_SHORT).show()
                            email = doc.data.getValue("email").toString()
                            location = doc.data.getValue("address").toString()
                            length = doc.data.getValue("length").toString()
                            width = doc.data.getValue("width").toString()
                            capacity = doc.data.getValue("capacity").toString()
                            groundtype = doc.data.getValue("groundType").toString()
                            services = doc.data.getValue("services").toString()
                            image = doc.data.getValue("image").toString()
                            pricePerPerson = doc.data.getValue("pricePerPerson").toString()
                            wholePrice = doc.data.getValue("wholePrice").toString()
                            //val openingtime = field.openingTimes

                        }
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
                        //intent.putExtra("openingTimes", openingtime)
                    }
                }

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

