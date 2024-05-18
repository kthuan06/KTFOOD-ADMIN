package com.example.adminktfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminktfood.adapter.MenuItemAdapter
import com.example.adminktfood.databinding.ActivityAllItemBinding
import com.example.adminktfood.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems : ArrayList<AllMenu> = ArrayList()

    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()


        binding.imageView8.setOnClickListener{
            finish()
        }

    }

    private fun retrieveMenuItem() {
        database  = FirebaseDatabase.getInstance()
        val FoodRe : DatabaseReference = database.reference.child("menu")

        FoodRe.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.clear()

                for (foodSnapshot  in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }
    private fun setAdapter() {
        val adapter = MenuItemAdapter(this@AllItemActivity, menuItems, databaseReference)
        binding.AllItemRecycel.layoutManager= LinearLayoutManager(this)
        binding.AllItemRecycel.adapter = adapter
    }
}