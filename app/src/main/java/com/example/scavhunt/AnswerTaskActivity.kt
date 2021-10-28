package com.example.scavhunt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.scavhunt.db.ScavItem
import com.google.android.material.textfield.TextInputLayout

class AnswerTaskActivity : AppCompatActivity() {
    var scavItem : ScavItem? = null

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> when(result.resultCode) {
            RESULT_OK -> {
                result.data?.let { intent ->
                    val scannedResult = intent.getStringExtra("result")
                    scannedResult?.let { string ->
                        val answerInput = findViewById<TextInputLayout>(R.id.answer_input)

                        scavItem?.let { item ->
                            answerInput.editText?.setText(string)
                            checkAnswer(item, answerInput)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_task)

        val answerTitle = findViewById<TextView>(R.id.answer_title)
        val answerDesc = findViewById<TextView>(R.id.answer_desc)
        val answerButton = findViewById<Button>(R.id.answer_check_answer)
        val answerInput = findViewById<TextInputLayout>(R.id.answer_input)
        val answerScanner = findViewById<Button>(R.id.answer_scanner)
        scavItem = intent.getParcelableExtra<ScavItem>("item")
        scavItem?.let { item ->
            answerTitle.text = item.title
            answerDesc.text = item.desc
            answerButton.setOnClickListener {
                checkAnswer(item, answerInput)
            }
        }
        answerScanner.setOnClickListener {
            launchQRActivity()
        }
    }
    private fun checkAnswer(item : ScavItem, answerInput: TextInputLayout) {
        if (answerInput.editText?.text.toString() == item.answer) {
            answerInput.error = null
            answerInput.hint = getString(R.string.answer_task_hint_correct)
            item.completed = true
        }
        else {
            answerInput.error = getString(R.string.answer_task_error)
            answerInput.hint = getString(R.string.answer_task_hint_default)
            item.completed = false
        }
    }
    private fun launchQRActivity() {
        val intent = Intent(this, QRScannerActivity::class.java)
        startForResult.launch(intent)
    }

    override fun onBackPressed() {

        val returnIntent = intent.apply {
            putExtra("item", scavItem)
        }
        setResult(Activity.RESULT_OK, returnIntent)

        super.onBackPressed()
    }
}