package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerView = findViewById(R.id.rvPuzzleImages)

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
            // Add more puzzle images here
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = PuzzleAdapter(puzzleList) { selectedPuzzleResId  ->
            startMainActivity(selectedPuzzleResId)
        }

        //puzzleAdapter = PuzzleAdapter(puzzleList, null)
        //recyclerView.adapter = puzzleAdapter
    }

    private fun startMainActivity(selectedPuzzleResId : Int) {
        val intent = Intent(this, PuzzleFullscreenActivity::class.java)
        intent.putExtra("selectedPuzzle", selectedPuzzleResId )
        startActivity(intent)
    }

}