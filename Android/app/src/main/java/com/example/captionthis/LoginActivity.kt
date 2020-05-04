package com.example.captionthis

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    companion object {
        val TAG = "Login Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        login_button.setOnClickListener {
            doLogin()
        }

        register_link.setOnClickListener {
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }




    }

    private fun forgotPassword(username : EditText){
        if (username.text.toString().isEmpty()) {
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }

        auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Email sent.", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun doLogin() {
        if (email_input.text.toString().isEmpty()) {
            email_input.error = "Please enter email"
            email_input.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_input.text.toString()).matches()) {
            email_input.error = "Please enter valid email"
            email_input.requestFocus()
            return
        }

        if (txt_pwd.text.toString().isEmpty()) {
            txt_pwd.error = "Please enter password"
            txt_pwd.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email_input.text.toString(), txt_pwd.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user,false)
                } else {

                    updateUI(null,false)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null) {
            currentUser.reload()
        }
        updateUI(currentUser,true)
    }

    private fun updateUI(currentUser: FirebaseUser?,  onStart: Boolean ) {

        if (currentUser != null) {
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, PhotoActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG,"Gets to verify email")
                currentUser.sendEmailVerification()
            }
        } else {
            if (onStart == true){
                Toast.makeText(
                    baseContext, "No user logged in",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG,"Fails Login")
            }
            else{
                Toast.makeText(
                    baseContext, "Login failed.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG,"Fails Login")
            }

        }
    }

}