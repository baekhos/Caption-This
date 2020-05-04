package com.example.captionthis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.Toast
import android.util.Log
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        val TAG = "Login Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        register_button.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            registerUser()
            startActivity(intent)
        }
        login_link.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


    }



    private fun registerUser() {


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

        if (pwd_input.text.length < 6){
            Toast.makeText(applicationContext,"Password too short, enter mimimum 6 charcters" , Toast.LENGTH_LONG).show()
            return
        }

        if (pwd_input.text.toString().isEmpty()) {
            pwd_input.error = "Please enter password"
            pwd_input.requestFocus()
            return
        }
        val email=email_input.text.toString()
        val password= pwd_input.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    sendEmailVerification()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }


            }
    }

    private fun sendEmailVerification() {
        // Disable button

        // Send verification email
        // [START send_email_verification]
        val user = auth.currentUser
        if (user !=null) {
            user.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    // [START_EXCLUDE]
                    // Re-enable button

                    if (task.isSuccessful) {
                        Toast.makeText(baseContext,
                            "Verification email sent to ${user.email} ",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(baseContext,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show()
                    }
                    // [END_EXCLUDE]
                }
        }

        // [END send_email_verification]
    }
}