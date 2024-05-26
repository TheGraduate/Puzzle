package com.example.hentaipazzlever1

import android.content.Context
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlin.math.abs

class PuzzleManager (private val context: Context) {

    private lateinit var puzzleGrid: GridLayout
    private lateinit var puzzleImages : IntArray
    private val puzzlePieces = mutableListOf<ImageView>()
    private var emptyCellIndex = 8
    private val solvedImage = R.drawable.full
    private var lastClickedIndex: Int? = null

    fun loadPuzzleImages(selectedPuzzle: Int): IntArray {
        return when (selectedPuzzle) {
            0 -> intArrayOf(
                R.drawable.first,
                R.drawable.sec,
                R.drawable.third,
                R.drawable.fourth,
                R.drawable.fifth,
                R.drawable.sixth,
                R.drawable.seventh,
                R.drawable.eigth,
                R.drawable.blank
            )
            1 -> intArrayOf(
                R.drawable.first,
                R.drawable.sec,
                R.drawable.third,
                R.drawable.fourth,
                R.drawable.fifth,
                R.drawable.sixth,
                R.drawable.seventh,
                R.drawable.eigth,
                R.drawable.blank
            )
            2 -> intArrayOf(
                R.drawable.first,
                R.drawable.sec,
                R.drawable.third,
                R.drawable.fourth,
                R.drawable.fifth,
                R.drawable.sixth,
                R.drawable.seventh,
                R.drawable.eigth,
                R.drawable.blank
            )
            // Добавьте сюда другие пазлы
            else -> intArrayOf(
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.mipmap.ic_placeholder,
                R.drawable.blank
            )
        }
    }

    fun setupPuzzle(gridLayout: GridLayout, puzzleImages: IntArray) {
        puzzleGrid = gridLayout
        this.puzzleImages = puzzleImages

        puzzleGrid.removeAllViews()
        puzzlePieces.clear()

        val gridSize = 3 // assuming a 3x3 puzzle
        val pieceSize = context.resources.displayMetrics.widthPixels / gridSize - 40

        for (i in puzzleImages.indices) {
            val imageView = ImageView(context)
            imageView.setImageResource(puzzleImages[i])
            imageView.layoutParams = GridLayout.LayoutParams().apply {
                width = pieceSize
                height = pieceSize
                /*rowSpec = GridLayout.spec(i / gridSize, 1f)
                columnSpec = GridLayout.spec(i % gridSize, 1f)*/
                setMargins(2, 2, 2, 2)


            }
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            //imageView.adjustViewBounds = true
            //imageView.setPadding(0, 0, 0, 0)
            imageView.setOnClickListener { onPuzzlePieceClick(it as ImageView, puzzleImages) }
            imageView.tag = i
            puzzlePieces.add(imageView)
        }

        puzzlePieces.shuffle()

        //emptyCellIndex = puzzlePieces.indexOfFirst { it.drawable.constantState == ContextCompat.getDrawable(context, R.drawable.blank)?.constantState }

        for (i in puzzlePieces.indices) {
            if (puzzleImages[i] == R.drawable.blank) {
                emptyCellIndex = i
                break
            }
        }

        //puzzlePieces.forEach { gridLayout.addView(it) }
        for (piece in puzzlePieces) {
            puzzleGrid.addView(piece)
        }

    }

   /* private fun onPuzzlePieceClick(view: ImageView) {
        val clickedIndex = puzzlePieces.indexOf(view)
        if (isAdjacent(clickedIndex, emptyCellIndex)) {
            swapPieces(clickedIndex, emptyCellIndex)
            emptyCellIndex = clickedIndex // Обновление индекса пустой ячейки после обмена
            updatePuzzleGrid()
            if (isPuzzleSolved(puzzleImages)) {
                showPuzzleSolved(puzzleGrid)
            }
        }
    }*/

    private fun onPuzzlePieceClick(view: ImageView, puzzleImages: IntArray) {
        //this.puzzleImages = puzzleImages

        val clickedIndex = puzzlePieces.indexOf(view)
        val lastClickedIndexLocal = lastClickedIndex

        if (lastClickedIndexLocal != null) {
            if (isAdjacent(clickedIndex, lastClickedIndexLocal)) {
                swapPieces(clickedIndex, lastClickedIndexLocal)
            }
            lastClickedIndex = null // Сбросить индекс после попытки перемещения
        } else {
            lastClickedIndex = clickedIndex // Сохранить индекс текущего нажатого фрагмента
        }
    }


    private fun isAdjacent(index1: Int, index2: Int): Boolean {
        val row1 = index1 / 3
        val col1 = index1 % 3
        val row2 = index2 / 3
        val col2 = index2 % 3

        return (row1 == row2 && abs(col1 - col2) == 1) || (col1 == col2 && abs(row1 - row2) == 1)
    }

    /*private fun swapPieces(index1: Int, index2: Int) {
        val temp = puzzlePieces[index1]
        puzzlePieces[index1] = puzzlePieces[index2]
        puzzlePieces[index2] = temp
        emptyCellIndex = index1
    }*/

    private fun swapPieces(index1: Int, index2: Int) {
        val temp = puzzlePieces[index1]
        puzzlePieces[index1] = puzzlePieces[index2]
        puzzlePieces[index2] = temp

        puzzleGrid.removeAllViews()
        for (piece in puzzlePieces) {
            puzzleGrid.addView(piece)
        }

        emptyCellIndex = index1

        if (::puzzleImages.isInitialized && isPuzzleSolved()) {
            showPuzzleSolved()
        }
    }

    /*private fun updatePuzzleGrid() {
        puzzleGrid.removeAllViews()
        puzzlePieces.forEach { puzzleGrid.addView(it) }
    }*/

    private fun isPuzzleSolved(): Boolean {
        //this.puzzleImages = puzzleImages
        if (!::puzzleImages.isInitialized) return false
        return puzzlePieces.withIndex().all { (index, imageView) ->
            val drawable = imageView.drawable
            val expectedDrawable = ContextCompat.getDrawable(context, puzzleImages[index])
            drawable.constantState == expectedDrawable?.constantState
        }
    }

    private fun showPuzzleSolved() {
        puzzleGrid.removeAllViews()
        ImageView(context).apply {
            setImageResource(solvedImage)
            layoutParams = GridLayout.LayoutParams().apply {
                width = GridLayout.LayoutParams.MATCH_PARENT
                height = GridLayout.LayoutParams.MATCH_PARENT
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            puzzleGrid.addView(this)
        }
    }

}