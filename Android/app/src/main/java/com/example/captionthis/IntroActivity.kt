package com.example.captionthis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        pdfView.fromAsset("Disertatie.pdf").load()
        next_button.setOnClickListener {
            val intent= Intent(this,PhotoActivity::class.java)
            startActivity(intent)
        }
    }
}
