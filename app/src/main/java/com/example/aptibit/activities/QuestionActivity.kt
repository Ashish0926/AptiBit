package com.example.aptibit.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aptibit.R
import com.example.aptibit.adapters.OptionAdapter
import com.example.aptibit.models.Aptitude
import com.example.aptibit.models.Question
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    private var index = 1
    private var aptitude: MutableList<Aptitude>? = null
    private var questionList: MutableMap<String, Question>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val title = intent.getStringExtra("title")
        topicTitle.text = title

        setupFirestore()
        setEventListeners()
    }

    private fun setEventListeners() {
        previous.setOnClickListener{
            index--
            bindQuestions()
        }
        next.setOnClickListener{
            index++
            bindQuestions()
        }
        submit.setOnClickListener{
            //Toast.makeText(this, "submitted", Toast.LENGTH_SHORT).show()
            Log.d("FINAL_ANS", questionList.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(aptitude!![0])
            intent.putExtra("aptitude", json)
            startActivity(intent)
            finish()
        }
    }

    private fun setupFirestore() {
        val title = intent.getStringExtra("title")
        val firestore = FirebaseFirestore.getInstance()
        if(title != null) {
            firestore.collection("Aptitudes").whereEqualTo("title", title)
                    .get()
                    .addOnSuccessListener {
                        if (it != null) {
                            Log.d("DATA", "successful")
                            aptitude = it.toObjects(Aptitude::class.java)
                            questionList = aptitude!![0].questions

                            bindQuestions()
                        }
                    }
        }
    }

    private fun bindQuestions() {

        previous.visibility = View.GONE
        next.visibility = View.GONE
        submit.visibility = View.GONE

        when (index) {
            1 -> {
                next.visibility = View.VISIBLE
            }
            questionList!!.size -> {
                submit.visibility = View.VISIBLE
                next.visibility = View.GONE
                previous.visibility = View.VISIBLE
            }
            else -> {
                next.visibility = View.VISIBLE
                previous.visibility = View.VISIBLE
            }
        }

        val question = questionList!!["question$index"]

//        val question = Question(
//            "Question",
//            "option1",
//            "option2",
//            "option3",
//            "option4",
//            "answer"
//        )

        if (question != null) {
            description.text = question.description
            val optionAdapter = OptionAdapter(this, question)
            this.recyclerView.layoutManager = LinearLayoutManager(this)
            this.recyclerView.adapter = optionAdapter
            this.recyclerView.setHasFixedSize(true)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}