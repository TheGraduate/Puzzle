package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PuzzleSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle_selection)

        val puzzleList = listOf(
            R.drawable.tyanochka1,
            R.drawable.tyanochka2,
            R.drawable.tyanochka3
            // Добавьте сюда миниатюры всех доступных пазлов
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = PuzzleAdapter(puzzleList) { selectedPuzzleIndex  ->
            startMainActivity(selectedPuzzleIndex)
        }
    }

    private fun startMainActivity(selectedPuzzleIndex : Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("selectedPuzzle", selectedPuzzleIndex )
        startActivity(intent)
    }

}