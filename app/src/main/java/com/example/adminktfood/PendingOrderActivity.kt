package com.example.adminktfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminktfood.adapter.PendingOrderAdapter
import com.example.adminktfood.databinding.ActivityPendingOrderBinding
import com.example.adminktfood.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {
    private lateinit var binding : ActivityPendingOrderBinding
    private var listOfName : MutableList<String> = mutableListOf()
    private var listOfPrice : MutableList<String> = mutableListOf()
    private var listOfImage : MutableList<String> = mutableListOf()
    private var listOfOrder : ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)

        binding.imageButton4.setOnClickListener {
            finish()
        }
        setContentView(binding.root)

        //init
        database = FirebaseDatabase.getInstance()

        databaseReference = database.reference.child("OrderDetails")

        getOrderDetails()

    }

    private fun getOrderDetails() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for(oderSS in snapshot.children){
                    val orderDetails = oderSS.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrder.add(it)
                    }
                }
                addDataToRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun addDataToRecyclerView() {
        for(orderItem in listOfOrder){
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfPrice.add(it) }
            orderItem.foodImage?.filterNot { it.isEmpty() }?.forEach{
                listOfImage.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.listOrder.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(this, listOfName,listOfPrice, listOfImage, this )
        binding.listOrder.adapter = adapter

    }


    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrder[position]
        intent.putExtra("UserOrderDetails", userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        val childeItemPushKey = listOfOrder[position].itemPushKey
        val clickItemOrderRef = childeItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderRef?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStt(position)
    }



    override fun onItemDispatchClickListener(position: Int) {
        val DisItemPushKey = listOfOrder[position].itemPushKey
        val clickItemOderRef = database.reference.child("CompleteOrder").child(DisItemPushKey!!)

        clickItemOderRef.setValue(listOfOrder[position]).addOnSuccessListener {
            deleteItemFromOrderDetails(DisItemPushKey)
        }
    }

    private fun deleteItemFromOrderDetails(disItemPushKey: String) {
        val orderDetailsItemsRef = database.reference.child("OrderDetails").child(disItemPushKey)
        orderDetailsItemsRef.removeValue()
            .addOnSuccessListener {
            Toast.makeText(this, "Order is Dispatched", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Order is not Dispatched", Toast.LENGTH_SHORT).show()
            }

    }

    private fun updateOrderAcceptStt(position: Int) {
        val userIDClick = listOfOrder[position].userUid
        val pushKeyOfClick = listOfOrder[position].itemPushKey
        val buyHistoryRef = database.reference.child("user").child(userIDClick!!)
            .child("History").child(pushKeyOfClick!!)
        buyHistoryRef.child("orderAccepted").setValue(true)
        databaseReference.child(pushKeyOfClick).child("orderAccepted").setValue(true)
    }
}