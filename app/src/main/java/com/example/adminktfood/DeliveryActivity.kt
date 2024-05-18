package com.example.adminktfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminktfood.adapter.DeliveryAdapter
import com.example.adminktfood.databinding.ActivityDeliveryBinding
import com.example.adminktfood.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DeliveryActivity : AppCompatActivity() {
    private val binding : ActivityDeliveryBinding by lazy {
        ActivityDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private  var listOfComplete : ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imageButton5.setOnClickListener { finish() }

        retrieveCompleteOrder()


    }

    private fun retrieveCompleteOrder() {
        database = FirebaseDatabase.getInstance()
        val completeRef = database.reference.child("CompleteOrder")
            .orderByChild("currentTime")
        completeRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfComplete.clear()
                for(completeSS in snapshot.children){
                    val completeOder = completeSS.getValue(OrderDetails::class.java)
                    completeOder?.let {
                        listOfComplete.add(it)
                    }
                }
                listOfComplete.reverse()

                setDataIntoRecycler()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setDataIntoRecycler() {
        val customerName = mutableListOf<String>()
        val moneyStt = mutableListOf<Boolean>()

        for(order in listOfComplete){
            order.userName?.let {
                customerName.add(it)
            }
            moneyStt.add(order.paymentRecieved)
        }
        val adapter = DeliveryAdapter(customerName, moneyStt)
        binding.deliveryRecycler.layoutManager = LinearLayoutManager(this)
        binding.deliveryRecycler.adapter = adapter

    }
}