package com.example.hatrick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hatrick.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


//        binding = FragmentProfileBinding.inflate(layoutInflater)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        val UserFireData = FirebaseFirestore.getInstance()
//        UserFireData.collection("users").whereEqualTo("customerID", getCurrentUserID2())
//            .get().addOnCompleteListener {
//                val result: StringBuffer = StringBuffer()
//                if (it.isSuccessful) {
//                    for (doc in it.result!!) {
//                        binding.firstNameBox.append(doc.data.getValue("firstName") as CharSequence?)
//                        binding.lastNameBox.append(doc.data.getValue("lastName") as CharSequence?)
//                        binding.phoneBox.append(doc.data.getValue("phoneNumber") as CharSequence?)
//                        binding.emailBox.append(doc.data.getValue("email") as CharSequence?)
//
//                    }
//
//                }
//
//            }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()
        val UserFireData = FirebaseFirestore.getInstance()
        UserFireData.collection("users").whereEqualTo("customerID", getCurrentUserID2())
            .get().addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if (it.isSuccessful) {
                    for (doc in it.result!!) {
                        binding.firstNameBox.append(doc.data.getValue("firstName") as CharSequence?)
                        binding.lastNameBox.append(doc.data.getValue("lastName") as CharSequence?)
                        binding.phoneBox.append(doc.data.getValue("phoneNumber") as CharSequence?)
                        binding.emailBox.append(doc.data.getValue("email") as CharSequence?)

                    }

                }

            }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}