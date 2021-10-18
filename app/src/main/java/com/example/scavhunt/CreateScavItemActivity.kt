package com.example.scavhunt

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scavhunt.db.ScavItem
import com.google.android.material.textfield.TextInputLayout

class CreateScavItemActivity : AppCompatActivity() {
    private var item: ScavItem = ScavItem("", "", "")
    private var index: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_scav_item)
        // Load components
        val titleInput = findViewById<TextInputLayout>(R.id.item_text_input_layout_title)
        val descInput = findViewById<TextInputLayout>(R.id.item_text_input_layout_desc)
        val answerInput = findViewById<TextInputLayout>(R.id.item_text_input_layout_answer)
        // Apply intent data
        val newitem = intent.getParcelableExtra<ScavItem>("item")
        index = intent.getIntExtra("index", -1)
        newitem?.let {
            titleInput.apply { editText?.setText(it.title) }
            descInput.apply { editText?.setText(it.desc) }
            answerInput.apply { editText?.setText(it.answer) }
        }
    }

    override fun onBackPressed() {

        // Load data back into ScavItem model
        val titleInput = findViewById<TextInputLayout>(R.id.item_text_input_layout_title)
        val descInput = findViewById<TextInputLayout>(R.id.item_text_input_layout_desc)
        val answerInput = findViewById<TextInputLayout>(R.id.item_text_input_layout_answer)

        item.apply {
            title = titleInput.editText?.text.toString()
            desc = descInput.editText?.text.toString()
            answer = answerInput.editText?.text.toString()
        }

        // Load data into return intent
        val returnIntent = intent.apply {
            putExtra("item", item)
            putExtra("index", index)
        }

        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }
}