package com.example.hentaipazzlever1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        /*recyclerView.adapter = PuzzleAdapter(puzzleList) { selectedPuzzle ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("selectedPuzzle", selectedPuzzle)
            startActivity(intent)
        }*/
        recyclerView.adapter = PuzzleAdapter(puzzleList) { selectedPuzzleIndex  ->
            startMainActivity(selectedPuzzleIndex )
        }
    }

    private fun startMainActivity(selectedPuzzleIndex : Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("selectedPuzzle", selectedPuzzleIndex )
        startActivity(intent)
    }

}

class PuzzleAdapter(
    private val puzzleList: List<Int>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<PuzzleAdapter.PuzzleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.puzzle_item, parent, false)
        return PuzzleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PuzzleViewHolder, position: Int) {
        holder.bind(puzzleList[position], clickListener)
    }

    override fun getItemCount(): Int = puzzleList.size

    class PuzzleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(puzzleResId: Int, clickListener: (Int) -> Unit) {
            val imageView = itemView.findViewById<ImageView>(R.id.puzzleImageView)
            imageView.setImageResource(puzzleResId)
            itemView.setOnClickListener { clickListener(position) }
        }
    }
}