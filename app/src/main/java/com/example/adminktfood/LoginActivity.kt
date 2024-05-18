package com.example.adminktfood

import android.content.ContentProviderClient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import android.util.Log
import android.widget.Toast
import com.example.adminktfood.databinding.ActivityLoginBinding
import com.example.adminktfood.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.signin.internal.SignInClientImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginActivity : AppCompatActivity() {
    private val nameShop : String? = null
    private lateinit var email : String
    private lateinit var passWord : String
    private lateinit var auth : FirebaseAuth
    private lateinit var dataBase : DatabaseReference
//

    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id).
//        )

        //initalize
        auth = Firebase.auth
        dataBase = Firebase.database.reference
//        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.loginbutton.setOnClickListener {
            email = binding.editTextTextEmailAddress.text.toString().trim()
            passWord = binding.editTextTextPassword.text.toString().trim()

            if(email.isBlank() || passWord.isBlank()){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email, passWord)
            }


        }
        binding.donotAcc.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, passWord: String) {
        auth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener{task ->
            if(task.isSuccessful){
                val user = auth.currentUser
                updateUi(user)
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        saveData()
                        updateUi(user)
                        Toast.makeText(this, "Create and login successful", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "createAccout: Failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveData() {
        TODO("Not yet implemented")
        email = binding.editTextTextEmailAddress.text.toString().trim()
        passWord = binding.editTextTextPassword.text.toString().trim()

        val user = UserModel(nameShop ,email, passWord)
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        userID?.let{
            dataBase.child("user").child(it).setValue(user)
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}