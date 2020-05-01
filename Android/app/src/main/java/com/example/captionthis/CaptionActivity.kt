package com.example.captionthis

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import kotlinx.android.synthetic.main.acivity_caption.*
import kotlinx.android.synthetic.main.activity_photo.*

class CaptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_caption)

        var image_path : String = intent.getStringExtra("imagePath");
        var fileUri = Uri.parse(image_path)
        //picture_image_view.setImageURI(fileUri)
        var photo= MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
        picture_image_view.setImageBitmap(photo)
        back_button.setOnClickListener {
            return_to_photo()
        }
        describe_button.setOnClickListener{
            get_description()
        }
    }
    fun return_to_photo(){
        val intent= Intent(this,PhotoActivity::class.java)
        startActivity(intent)
    }

    fun get_description(){
        // To be further implemented with firebase and image caption model
        description_text.text="Descriere poza"
    }



}
