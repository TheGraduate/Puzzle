package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PuzzleFullscreenActivity : AppCompatActivity() {
    private lateinit var fullScreenImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle_fullscreen)

        //val imageUrl = intent.getStringExtra("selectedPuzzle")
        val selectedPuzzleResId = intent.getIntExtra("selectedPuzzle", -1)
        //val intent = Intent(this, PuzzleFullscreenActivity::class.java)
        //intent.putExtra("selectedPuzzle", selectedPuzzleResId )

        fullScreenImageView = findViewById(R.id.fullScreenImageView)
            //fullScreenImageView = findViewById(R.id.fullScreenImageView)
        if (selectedPuzzleResId != -1) {
            fullScreenImageView.setImageResource(selectedPuzzleResId)
        }

    }
}