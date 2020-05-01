package com.example.captionthis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register_button.setOnClickListener {
            val intent= Intent(this,PhotoActivity::class.java)
            startActivity(intent)
        }
        login_link.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }
}