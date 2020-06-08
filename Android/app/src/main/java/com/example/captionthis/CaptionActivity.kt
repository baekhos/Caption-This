@file:Suppress("DEPRECATION")

package com.example.captionthis

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast

import java.io.File

import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import kotlinx.android.synthetic.main.acivity_caption.*

import com.example.captionthis.networking.ApiConfig
import com.example.captionthis.networking.AppConfig
import com.example.captionthis.networking.ServerResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CaptionActivity : AppCompatActivity() {

    private var postPath: String? = null



    private var contentDescription: String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_caption)

        var image_path : String = intent.getStringExtra("imagePath");
        var fileUri = Uri.parse(image_path)
        var photo= MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
        picture_image_view.setImageBitmap(photo)
        back_button.setOnClickListener {
            return_to_photo()
        }
        describe_button.setOnClickListener{
            get_description()
        }
        val selectedImage = fileUri
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
        assert(cursor != null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        postPath =cursor.getString(columnIndex)

    }
    fun return_to_photo(){
        val intent= Intent(this,PhotoActivity::class.java)
        startActivity(intent)
    }

    fun get_description(){
        uploadFile()
    }

    private fun uploadFile() {
        if (postPath == null || postPath == "") {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show()
            return
        } else {

            // Map is used to multipart the file using okhttp3.RequestBody
            val map = HashMap<String, RequestBody>()
            val file = File(postPath!!)

            // Parsing any Media type file
            val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
            map.put("file\"; filename=\"" + file.name + "\"", requestBody)
            val getResponse = AppConfig.getRetrofit().create(ApiConfig::class.java)
            val call = getResponse.upload("token", map)
            call.enqueue(object : Callback<ServerResponse> {
                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val serverResponse = response.body()
                            //Toast.makeText(applicationContext, serverResponse?.message, Toast.LENGTH_SHORT).show()
                            contentDescription=serverResponse?.message
                            description_text.text=contentDescription
                        }
                    } else {
                        Toast.makeText(applicationContext, "problem uploading image", Toast.LENGTH_SHORT).show()
                        description_text.text="problem uploading image"
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.v("Response gotten is", t.message)
                }
            })
        }
    }




}
