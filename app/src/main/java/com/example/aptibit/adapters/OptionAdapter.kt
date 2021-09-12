package com.example.aptibit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aptibit.R
import com.example.aptibit.models.Question

class OptionAdapter(private val context: Context, private val question: Question): RecyclerView.Adapter<OptionAdapter.OptionViewHolder>(){

    inner class OptionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val optionText: TextView = itemView.findViewById(R.id.optionText)
        val optionBox: ImageView = itemView.findViewById(R.id.optionBox)
    }

    private var options: List<String> = listOf(question.option1, question.option2, question.option3, question.option4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.optionText.text = options[position]

        holder.itemView.setOnClickListener{
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }

        if(question.userAnswer == options[position]){
            holder.optionBox.setImageResource(R.drawable.option_filler)
        }else{
            holder.optionBox.setImageResource(R.drawable.card_border)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }


}