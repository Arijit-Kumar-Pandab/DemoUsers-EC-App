package com.example.demoecommerce.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.demoecommerce.R
import com.example.demoecommerce.activity.LoginActivity
import com.example.demoecommerce.adapter.AllOrderAdapter
import com.example.demoecommerce.databinding.FragmentMoreBinding
import com.example.demoecommerce.model.AllOrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MoreFragment : Fragment() {
    private lateinit var binding : FragmentMoreBinding
    private lateinit var list : ArrayList<AllOrderModel>
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        list = ArrayList()

        val preferences = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId", preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
            list.clear()
            for (doc in it) {
                val data = doc.toObject(AllOrderModel::class.java)
                list.add(data)
            }
            binding.recyclerView.adapter = AllOrderAdapter(list, requireContext())
        }

        binding.floatingActionButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))

        }
        return binding.root
    }

}