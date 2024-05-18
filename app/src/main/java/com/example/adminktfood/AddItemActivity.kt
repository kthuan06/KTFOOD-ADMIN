package com.example.adminktfood

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminktfood.databinding.ActivityAddItemBinding
import com.example.adminktfood.model.AllMenu
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngredient:String
    private  var foodImage: Uri? = null

    //firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var dataBase : FirebaseDatabase

    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance()

        binding.btnadd.setOnClickListener {
            //get data
            foodName = binding.editText.text.toString().trim()
            foodPrice = binding.editText2.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredient.text.toString().trim()

            if(!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this, "Add item successful", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Fill all", Toast.LENGTH_SHORT).show()
            }

        }

        binding.textView15.setOnClickListener {
            pickImage.launch("image/*")
        }

        //select image
//        binding.textView15.setOnClickListener {
//            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.GetContent))
//        }
        //back
        binding.imageView8.setOnClickListener{
            finish()
        }

    }

    private fun uploadData() {
        val menuRe = dataBase.getReference("menu")

        val newKey = menuRe.push().key

        if(foodImage != null){
            val storageRe = FirebaseStorage.getInstance().reference
            val imageRe = storageRe.child("menu_images/${newKey}.jpg")
            //foodImage not null
            val uploadTask = imageRe.putFile(foodImage!!)
            uploadTask.addOnSuccessListener {
                imageRe.downloadUrl.addOnSuccessListener{
                        downloadUrl->
//                    Create new menu item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUrl.toString(),
                    )
                        newKey?.let{
                            key ->
                            menuRe.child(key).setValue(newItem).addOnSuccessListener {
                                Toast.makeText(this, "Data uploads successful", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener{
                                Toast.makeText(this, "Data uploads failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }   .addOnFailureListener{
                    Toast.makeText(this, "Image uploads failed", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Select image!", Toast.LENGTH_SHORT).show()
        }
    }



    val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        if(uri != null){
            binding.selectedimage.setImageURI(uri)
            foodImage =uri
        }
    }
}