package com.example.aptibit.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aptibit.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
//        if(auth.currentUser != null){
//            Toast.makeText
//        }
        loginButton.setOnClickListener{
            loginUser()
        }

        signUpBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser() {
        val email = editTextEmailAddress.text.toString()
        val password = editTextPassword.text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this, "email or password field can't be empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Log.d(TAG, "logInWithEmail:success")
                    Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("user_id", email)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "logInWithEmail:failure", it.exception)
                }
            }
    }
}