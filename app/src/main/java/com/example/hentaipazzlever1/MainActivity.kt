package com.example.hentaipazzlever1

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var puzzleGrid: GridLayout
    private lateinit var puzzleImages : List<Bitmap> //IntArray
    private lateinit var puzzleManager: PuzzleManager
    private lateinit var backBtnToSelection: Button
    //private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backBtnToSelection = findViewById(R.id.btn_back_to_selection)
        backBtnToSelection.setOnClickListener {
            val intent = Intent(this, PuzzleSelectionActivity::class.java)
            startActivity(intent)
        }

        val selectedPuzzleResId = intent.getIntExtra("selectedPuzzle", -1)
        /*puzzleManager = PuzzleManager(this)
        puzzleImages = puzzleManager.loadPuzzleImages(selectedPuzzleResId)

        puzzleGrid = findViewById(R.id.puzzleGrid)
        puzzleManager.setupPuzzle(puzzleGrid, puzzleImages)*/
        if (selectedPuzzleResId != -1) {
            puzzleManager = PuzzleManager(this, selectedPuzzleResId)
            puzzleImages = puzzleManager.loadPuzzleImages(selectedPuzzleResId)

            puzzleGrid = findViewById(R.id.puzzleGrid)
            puzzleManager.setupPuzzle(puzzleGrid, puzzleImages)
        }

    }

}