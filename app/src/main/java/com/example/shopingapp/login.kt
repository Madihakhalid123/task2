package com.example.shopingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopingapp.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class login : AppCompatActivity() {
    var db = Firebase.firestore
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.signup.setOnClickListener() {
            startActivity(Intent(this@login,signup::class.java))
        }
        binding.apply {

            lgn.setOnClickListener {
                if (email.text.toString().isEmpty() && pasword.text.toString().isEmpty()) {
                    Toast.makeText(this@login, "Please fill email and password", Toast.LENGTH_SHORT).show()
                } else {





                    db.collection("User")
                        .whereEqualTo("email", email.text.toString())
                        .whereEqualTo("password", pasword.text.toString())
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val querySnapshot = task.result
                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                    val documentId = querySnapshot.documents[0].id

                                    val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("IsLoggedIn",true)
                                    editor.apply()
                                    editor.putString("userId", documentId)
                                    editor.apply()

                                    Toast.makeText(this@login, "Login Successful", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@login, home::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@login, "Invalid email or password", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@login, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }




        }


}
    }

