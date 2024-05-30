package com.example.hentaipazzlever1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PuzzleAdapter(
    private val puzzleList: List<Int>,
    //private val clickListener: (Int) -> Unit
    private val onPuzzleSelected: ((Int) -> Unit?)?
) : RecyclerView.Adapter<PuzzleAdapter.PuzzleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.puzzle_item, parent, false)
        return PuzzleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PuzzleViewHolder, position: Int) {
        //holder.bind(puzzleList[position], clickListener)
        val puzzleResId = puzzleList[position]
        holder.bind(puzzleResId)
    }

    override fun getItemCount(): Int = puzzleList.size

    inner class PuzzleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.puzzleImageView)
        fun bind(puzzleResId: Int/*, clickListener: (Int) -> Unit*/) {
            //val imageView = itemView.findViewById<ImageView>(R.id.puzzleImageView)

            imageView.setImageResource(puzzleResId)
            itemView.setOnClickListener {
                //clickListener(adapterPosition )
                onPuzzleSelected?.let { it -> it(puzzleResId) }
            }
        }
    }
}