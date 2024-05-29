package com.example.hentaipazzlever1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.GridLayout
import android.widget.ImageView
import kotlin.math.abs

class PuzzleManager(private val context: Context, private val selectedPuzzleResId: Int) {

    private lateinit var puzzleGrid: GridLayout
    private lateinit var initialPositions: MutableList<Int>
    private val puzzlePieces = mutableListOf<ImageView>()
    private var lastClickedIndex: Int? = null
    //private val solvedImage = selectedPuzzleResId

    private fun loadBitmapFromResource(resourceId: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, resourceId)
    }

    private fun splitBitmap(bitmap: Bitmap, rows: Int, cols: Int): List<Bitmap> {
        val pieces = mutableListOf<Bitmap>()
        val pieceWidth = bitmap.width / cols
        val pieceHeight = bitmap.height / rows

        initialPositions = mutableListOf<Int>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val x = col * pieceWidth
                val y = row * pieceHeight
                pieces.add(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceHeight))
                initialPositions.add(row * cols + col)
            }
        }

        return pieces
    }

    fun loadPuzzleImages(resourceId: Int, rows: Int = 3, cols: Int = 3): List<Bitmap> {
        val bitmap = loadBitmapFromResource(resourceId)
        return splitBitmap(bitmap, rows, cols)
    }

    fun setupPuzzle(gridLayout: GridLayout, puzzleImages: List<Bitmap>/*, solvedImageId: Int*/) {
        puzzleGrid = gridLayout

        puzzleGrid.removeAllViews()
        puzzlePieces.clear()

        val gridSize = 3 // assuming a 3x3 puzzle
        val pieceSize = context.resources.displayMetrics.widthPixels / gridSize - 40

        for (i in puzzleImages.indices) {
            val imageView = ImageView(context)
            imageView.setImageBitmap(puzzleImages[i])
            imageView.layoutParams = GridLayout.LayoutParams().apply {
                width = pieceSize
                height = pieceSize
                setMargins(2, 2, 2, 2)
            }
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setOnClickListener { onPuzzlePieceClick(it as ImageView) }
            imageView.tag = i
            puzzlePieces.add(imageView)
        }

        puzzlePieces.shuffle()

        for (piece in puzzlePieces) {
            puzzleGrid.addView(piece)
        }

        //solvedImage = solvedImageId
    }

    private fun onPuzzlePieceClick(view: ImageView) {
        val clickedIndex = puzzlePieces.indexOf(view)
        val lastClickedIndexLocal = lastClickedIndex

        if (lastClickedIndexLocal != null) {
            if (isAdjacent(clickedIndex, lastClickedIndexLocal)) {
                swapPieces(clickedIndex, lastClickedIndexLocal)
                animatePiece(view, false) // Опускаем элемент после перестановки
            } else {
                // Несоседний элемент был выбран, опускаем уже выбранный кусочек обратно
                animatePiece(puzzlePieces[lastClickedIndexLocal], false)
            }
            lastClickedIndex = null
        } else {
            lastClickedIndex = clickedIndex
            animatePiece(view, true)
        }
    }

    private fun isAdjacent(index1: Int, index2: Int): Boolean {
        val row1 = initialPositions[index1] / 3
        val col1 = initialPositions[index1] % 3
        val row2 = initialPositions[index2] / 3
        val col2 = initialPositions[index2] % 3

        return (row1 == row2 && abs(col1 - col2) == 1) || (col1 == col2 && abs(row1 - row2) == 1)
    }

    private fun swapPieces(index1: Int, index2: Int) {
        val temp = puzzlePieces[index1]
        puzzlePieces[index1] = puzzlePieces[index2]
        puzzlePieces[index2] = temp

        puzzleGrid.removeAllViews()
        for (piece in puzzlePieces) {
            puzzleGrid.addView(piece)
        }

        animatePiece(puzzlePieces[index1], false)

        if (::initialPositions.isInitialized && isPuzzleSolved()) {
            showPuzzleSolved()
        }
    }

    private fun animatePiece(view: ImageView, highlight: Boolean) {
        val scale = if (highlight) 1.1f else 1.0f
        val elevation = if (highlight) 10f else 0f

        view.animate()
            .scaleX(scale)
            .scaleY(scale)
            .translationZ(elevation) // используем translationZ для эффекта поднятия/опускания
            .setDuration(200)
            .start()
    }

    private fun isPuzzleSolved(): Boolean {
        if (!::initialPositions.isInitialized) return false
        return puzzlePieces.withIndex().all { (index, imageView) ->
            initialPositions[index] == imageView.tag as? Int
        }
    }

    private fun showPuzzleSolved() {
        puzzleGrid.removeAllViews()
        ImageView(context).apply {
            setImageResource(selectedPuzzleResId)
            layoutParams = GridLayout.LayoutParams().apply {
                width = GridLayout.LayoutParams.MATCH_PARENT
                height = GridLayout.LayoutParams.MATCH_PARENT
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            puzzleGrid.addView(this)
        }
    }

}