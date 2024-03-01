package com.example.shopingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class splash_screen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)


 val sharedPref: SharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()


            Handler().postDelayed({
                if (!sharedPref.getBoolean("IsLoggedIn",false))
                {
                    Toast.makeText(this@splash_screen, sharedPref.getBoolean("IsLoggedIn",false).toString(), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, login::class.java))
                } else {
                    startActivity(Intent(this, home::class.java))
                }
                finish()
            },SPLASH_TIME_OUT)
        }


    }


