package com.example.aptibit.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.example.aptibit.R
import com.example.aptibit.models.Aptitude
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var aptitude: Aptitude

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        firebaseAuth = FirebaseAuth.getInstance()

        setupViews()
        setupListeners()
    }

    private fun setupListeners() {
        home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        logout.setOnClickListener{
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViews() {
        val aptitudeData = intent.getStringExtra("aptitude")
        aptitude = Gson().fromJson(aptitudeData, Aptitude::class.java)

        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for(entry in aptitude.questions.entries){
            val question = entry.value
            builder.append("<font color '#a6a3a1'><b>Question : ${question.description}</b></font><br/><br/>")
            builder.append("<font color '#F75355'><b>Answer : ${question.answer}</b></font><br/><br/>")
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            answerText.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
        }else{
            answerText.text = Html.fromHtml(builder.toString())
        }
    }

    private fun calculateScore() {
        var score = 0
        for(entry in aptitude.questions.entries){
            val question = entry.value
            if(question.userAnswer == question.answer){
                score += 10
            }
        }
        "Score : $score".also { scoreText.text = it }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}