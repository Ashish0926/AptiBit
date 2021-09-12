package com.example.aptibit.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aptibit.R
import com.example.aptibit.models.Aptitude
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email
        if(email != null && email != ""){
            userId.text = email
        }

        logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show()
        }

        setupFirestore()

        trainProblems.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Train Problem")
            startActivity(intent)
            finish()
        }

        heights_distance.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Heights & Distance")
            startActivity(intent)
            finish()
        }

        profit_loss.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Profit & Loss")
            startActivity(intent)
            finish()
        }

        age_problems.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Age Problems")
            startActivity(intent)
            finish()
        }

        boat_problems.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Boats & Stream")
            startActivity(intent)
            finish()
        }

        time_work.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Time & Work")
            startActivity(intent)
            finish()
        }

        interest.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Interest Problems")
            startActivity(intent)
            finish()
        }

        numbers.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Numbers")
            startActivity(intent)
            finish()
        }

        permutation_combination.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Permutation & Combination")
            startActivity(intent)
            finish()
        }

        speed.setOnClickListener {
            val intent = Intent(this@MainActivity, QuestionActivity::class.java)
            intent.putExtra("title", "Speed")
            startActivity(intent)
            finish()
        }
    }



    private fun setupFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("Aptitudes")

        // every time our collection gets updated addSnapshotListener identifies the change
        collectionReference.addSnapshotListener { value, error ->
            if(value == null || error != null){
                Toast.makeText(this, "error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Aptitude::class.java).toString())
        }
    }
}