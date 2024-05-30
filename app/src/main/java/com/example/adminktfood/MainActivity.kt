package com.example.adminktfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminktfood.databinding.ActivityMainBinding
import com.example.adminktfood.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database : FirebaseDatabase
    private lateinit var auth : FirebaseAuth
    private lateinit var completeOrderReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.cardView2.setOnClickListener{
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.cardView4.setOnClickListener{
            val intent = Intent(this, AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.imageView2.setOnClickListener {
            val intent = Intent(this, PendingOrderActivity::class.java)
            startActivity(intent)
        }
        binding.cardView5.setOnClickListener{
            val intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.cardView3.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        pendingOrders()

        completeOrder()

        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {

        var listOfTotalPay = mutableListOf<Int>()
        var complete = FirebaseDatabase.getInstance().reference.child("CompleteOrder")
        complete.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              for (orderSS in snapshot.children){
                  var completeOrder = orderSS.getValue(OrderDetails::class.java)
                  completeOrder?.totalPrice?.replace("$", "")?.toIntOrNull()
                      ?.let {
                        i-> listOfTotalPay.add(i)
                      }
              }
                binding.mneared.text ="$" + listOfTotalPay.sum().toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun completeOrder() {
        database = FirebaseDatabase.getInstance()
        var completeOrderRef = database.reference.child("CompleteOrder")
        var completeItem = 0
        completeOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completeItem = snapshot.childrenCount.toInt()
                binding.textView9.text = completeItem.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        var pendingOrderRef= database.reference.child("OrderDetails")
        var pendingItem = 0
        pendingOrderRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingItem = snapshot.childrenCount.toInt()
                binding.textView10.text = pendingItem.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}