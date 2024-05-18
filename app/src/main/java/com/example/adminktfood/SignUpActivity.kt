package com.example.adminktfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.adminktfood.databinding.ActivitySignUpBinding
import com.example.adminktfood.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlin.math.log

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var nameShop : String
    private lateinit var email : String
    private lateinit var passWord : String
    private lateinit var dataBase : DatabaseReference



    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Firebase auth
        auth = Firebase.auth
        dataBase = Firebase.database.reference


        binding.loginbutton.setOnClickListener {
            nameShop = binding.nameRes.text.toString().trim()
            email = binding.editTextTextEmailAddress.text.toString().trim()
            passWord = binding.editTextTextPassword.text.toString().trim()

            if(nameShop.isBlank() || email.isBlank() || passWord.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email, passWord)
            }


        }
        binding.donotAcc.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val locationList = arrayOf("Hòa Hải", "Hòa Quý","Đà Nẵng" )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoComplete = binding.listOfLocation
        autoComplete.setAdapter(adapter)
    }

    private fun createAccount(email: String, passWord: String) {
        auth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener{
            task -> if(task.isSuccessful){
            Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
            saveData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            Log.d("Acount", "createAccount: Failure", task.exception)
        }

        }
    }

    private fun saveData() {
        nameShop = binding.nameRes.text.toString().trim()
        email = binding.editTextTextEmailAddress.text.toString().trim()
        passWord = binding.editTextTextPassword.text.toString().trim()
        val user = UserModel(nameShop, email, passWord)
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        dataBase.child("user").child(userID).setValue(user)
    }
}