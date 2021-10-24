package com.example.scavhunt

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.scavhunt.db.ScavItem
import com.google.android.material.textfield.TextInputLayout

class AnswerTaskActivity : AppCompatActivity() {
    var scavItem : ScavItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_task)

        val answerTitle = findViewById<TextView>(R.id.answer_title)
        val answerDesc = findViewById<TextView>(R.id.answer_desc)
        val answerButton = findViewById<Button>(R.id.answer_check_answer)
        val answerInput = findViewById<TextInputLayout>(R.id.answer_input)
        scavItem = intent.getParcelableExtra<ScavItem>("item")
        scavItem?.let { item ->
            answerTitle.text = item.title
            answerDesc.text = item.desc
            answerButton.setOnClickListener {
                if (answerInput.editText?.text.toString() == item.answer) {
                    answerInput.error = null
                    answerInput.hint = "Correct!"
                    item.completed = true
                }
                else {
                    answerInput.error = "Incorrect answer"
                    answerInput.hint = "Input answer"
                    item.completed = false
                }
            }
        }
    }

    override fun onBackPressed() {

        val returnIntent = intent.apply {
            putExtra("item", scavItem)
        }
        setResult(Activity.RESULT_OK, returnIntent)

        super.onBackPressed()
    }
}