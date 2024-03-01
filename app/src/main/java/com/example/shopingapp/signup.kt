package com.example.shopingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopingapp.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class signup : AppCompatActivity() {
    private var db = Firebase.firestore
    private lateinit var binding: ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lgn.setOnClickListener()
        {
            startActivity(Intent(this@signup, login::class.java))
        }


        binding.apply {


            signup.setOnClickListener()
            {


                var modelUser = model()

                if (mail.text.toString().isEmpty() && password.text.toString()
                        .isEmpty() && name.text.toString().isEmpty()
                ) {
                    Toast.makeText(this@signup, "please Fill name and email", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.text.toString().length < 6) {
                    Toast.makeText(this@signup, "Invalid password format", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.text.toString() != confirmPassword.text.toString()) {
                    Toast.makeText(this@signup, "password not matched", Toast.LENGTH_SHORT).show()
                } else {
                    modelUser.email = mail.text.toString()
                    modelUser.password = password.text.toString()
                    modelUser.name = name.text.toString()
                    db.collection("User").add(modelUser)
                        .addOnSuccessListener { documentreference ->


                            modelUser.userId = documentreference.id
                            db.collection("User").document(documentreference.id).set(modelUser)


                            Toast.makeText(this@signup, "SignUp Successfull", Toast.LENGTH_SHORT)
                                .show()


                        }


                        .addOnFailureListener()
                        {
                            Toast.makeText(this@signup, "Failed to SignUp", Toast.LENGTH_SHORT)
                                .show()
                        }
                }

            }
        }
    }

}