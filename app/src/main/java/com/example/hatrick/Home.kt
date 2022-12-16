package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_home, container, false)
        val fCArd = v.findViewById<CardView>(R.id.footballCard)
        val FRules = v.findViewById<ImageView>(R.id.footballRulesImg)
        FRules.setOnClickListener {
            var dial = FootballRules()
            dial.show(childFragmentManager,"FootballRules")
        }
        val BRules = v.findViewById<ImageView>(R.id.basketballRulesImg)
        BRules.setOnClickListener {
            var dial = BasketballRules()
            dial.show(childFragmentManager,"BasketballRules")
        }
        val TRules = v.findViewById<ImageView>(R.id.tennisRulesImg)
        TRules.setOnClickListener {
            var dial = TennisRules()
            dial.show(childFragmentManager,"Tennis Rules")
        }
        val HRules = v.findViewById<ImageView>(R.id.handballRulesImg)
        HRules.setOnClickListener {
            var dial = HandballRules()
            dial.show(childFragmentManager,"Handball Rules")
        }
        val BmRules = v.findViewById<ImageView>(R.id.badmintonRulesImg)
        BmRules.setOnClickListener {
            var dial = BadmintonRules()
            dial.show(childFragmentManager,"Badminton Rules")
        }
        val VRules = v.findViewById<ImageView>(R.id.volleyballRulesImg)
        VRules.setOnClickListener {
            var dial = VolleyballRules()
            dial.show(childFragmentManager,"Volleyball Rules")
        }
        fCArd.setOnClickListener {
            val intent = Intent(this@Home.requireContext(),Football::class.java)
            startActivity(intent)
        }
        val FF = v.findViewById<TextView>(R.id.footballLab)
        FF.setOnClickListener {
                Toast.makeText(context,"Football", Toast.LENGTH_SHORT).show()
        }
//        val FRules = v.findViewById<TextView>(R.id.footballRulesImg)
//        FRules.setOnClickListener {
//            var dial = FootballRules()
//            dial.show(childFragmentManager,"FootballRules")
//        }
        // Inflate the layout for this fragment
        return v
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}