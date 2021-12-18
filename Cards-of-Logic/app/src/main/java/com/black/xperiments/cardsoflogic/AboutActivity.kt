package com.black.xperiments.cardsoflogic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.black.xperiments.cardsoflogic.databinding.ActivityAboutBinding
import com.black.xperiments.cardsoflogic.logic.Cards

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    private var tempCard = Cards()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tempCard  = intent.getSerializableExtra("card") as Cards

        binding.logic.text = tempCard.title
        binding.explanation.text = tempCard.explanation
        binding.examples.text = tempCard.example
        binding.conclusion.text = tempCard.conclusion
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}