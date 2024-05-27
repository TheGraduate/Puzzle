package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PuzzleSelectionActivity : AppCompatActivity() {
    private lateinit var backBtnToMenu: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle_selection)

        val puzzleList = listOf(
            R.drawable.tyanochka1,
            R.drawable.tyanochka2,
            R.drawable.tyanochka3,
            R.drawable.tyanochka1,
            R.drawable.tyanochka2,
            R.drawable.tyanochka3,
            R.drawable.tyanochka1,
            R.drawable.tyanochka2,
            R.drawable.tyanochka3,
            R.drawable.tyanochka3,
            // Добавьте сюда миниатюры всех доступных пазлов
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = PuzzleAdapter(puzzleList) { selectedPuzzleIndex  ->
            startMainActivity(selectedPuzzleIndex)
        }

        backBtnToMenu = findViewById(R.id.btn_back_to_menu_from_puzzle_selection)
        backBtnToMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

    }

    private fun startMainActivity(selectedPuzzleIndex : Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("selectedPuzzle", selectedPuzzleIndex )
        startActivity(intent)
    }

}