package com.example.adminktfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminktfood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
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
    }
}