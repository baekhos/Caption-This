package com.example.captionthis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.setOnClickListener {
            val intent= Intent(this,PhotoActivity::class.java)
            startActivity(intent)
        }
        register_link.setOnClickListener {
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}