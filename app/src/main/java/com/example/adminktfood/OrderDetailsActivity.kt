package com.example.adminktfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminktfood.adapter.OderDetailsAdapter
import com.example.adminktfood.databinding.ActivityOrderDetailsBinding
import com.example.adminktfood.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding : ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var userName : String? = null
    private var address : String? = null
    private var phoneNumber : String? = null
    private var totalPrice : String? = null

    private var listOfName : ArrayList<String> = arrayListOf()
    private var listOfPrice : ArrayList<String> = arrayListOf()
    private var listOfImage : ArrayList<String> = arrayListOf()
    private var listOfQuantity : ArrayList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageButton6.setOnClickListener {
            finish()
        }



        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val recevedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        recevedOrderDetails?.let {orderDetails ->

                userName = recevedOrderDetails.userName
                listOfName = recevedOrderDetails.foodName as ArrayList<String>
                listOfImage = recevedOrderDetails.foodImage  as ArrayList<String>
                listOfQuantity = recevedOrderDetails.foodQuantity  as ArrayList<Int>
                address = recevedOrderDetails.address
                phoneNumber = recevedOrderDetails.phoneNumber
                listOfPrice = recevedOrderDetails.foodPrice  as ArrayList<String>
                totalPrice = recevedOrderDetails.totalPrice


                setUserData()
                setAdapter()

        }

    }

    private fun setAdapter() {
        binding.recyclerDetails.layoutManager = LinearLayoutManager(this)
        val adapter = OderDetailsAdapter(this, listOfName, listOfImage, listOfPrice, listOfQuantity)
        binding.recyclerDetails.adapter= adapter
    }

    private fun setUserData() {
        binding.editn.text = Editable.Factory.getInstance().newEditable(userName)
        binding.editaddress.text = Editable.Factory.getInstance().newEditable(address)
        binding.editphone.text = Editable.Factory.getInstance().newEditable(phoneNumber)
        binding.ttprice.text = Editable.Factory.getInstance().newEditable(totalPrice)



    }
}