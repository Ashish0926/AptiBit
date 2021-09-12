package com.example.aptibit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aptibit.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_intro.*

class LoginIntro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        nextBtn.setOnClickListener{
            redirect("LOGIN")
        }
    }

    private fun redirect(s: String) {
        val intent = when (s){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> {
                Intent(this, MainActivity::class.java)
            }
            else -> throw Exception("no path exist")
        }
        startActivity(intent)
        finish()
    }
}