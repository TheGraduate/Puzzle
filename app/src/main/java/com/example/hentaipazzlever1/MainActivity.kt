package com.example.hentaipazzlever1

import android.os.Bundle
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var puzzleGrid: GridLayout
    private lateinit var puzzleImages : IntArray /*= intArrayOf(
        R.drawable.first,
        R.drawable.sec,
        R.drawable.third,
        R.drawable.fourth,
        R.drawable.fifth,
        R.drawable.sixth,
        R.drawable.seventh,
        R.drawable.eigth,
        R.drawable.blank // Пустая ячейка
    )*/
    private val puzzlePieces = mutableListOf<ImageView>()
    private var emptyCellIndex = 8 // Индекс пустой ячейки
    /*private val solvedImages = intArrayOf(
        R.drawable.full // Картинка после завершения пазла
    )*/
    private val solvedImages = R.drawable.full

    private var lastClickedIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectedPuzzleIndex = intent.getIntExtra("selectedPuzzle", -1)
        loadPuzzleImages(selectedPuzzleIndex)

        puzzleGrid = findViewById(R.id.puzzleGrid)
        setupPuzzle()

    }

    private fun loadPuzzleImages(selectedPuzzle: Int) {
        puzzleImages = when (selectedPuzzle) {
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

    private fun setupPuzzle() {
        puzzleGrid.removeAllViews()
        puzzlePieces.clear()

        val gridSize = 3 // assuming a 3x3 puzzle
        val pieceSize = resources.displayMetrics.widthPixels / gridSize - 40

        for (i in puzzleImages.indices) {
            val imageView = ImageView(this)
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
            imageView.setOnClickListener { onPuzzlePieceClick(it as ImageView) }
            imageView.tag = i
            puzzlePieces.add(imageView)
        }

        puzzlePieces.shuffle()

        for (i in puzzlePieces.indices) {
            if (puzzleImages[i] == R.drawable.blank) {
                emptyCellIndex = i
                break
            }
        }

        for (piece in puzzlePieces) {
            puzzleGrid.addView(piece)
        }
    }

  /*  private fun onPuzzlePieceClick(view: ImageView) {
        val clickedIndex = puzzlePieces.indexOf(view)
        if (isAdjacent(clickedIndex, emptyCellIndex)) {
            swapPieces(clickedIndex, emptyCellIndex)
        }
    }*/

    private fun onPuzzlePieceClick(view: ImageView) {
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

    private fun swapPieces(index1: Int, index2: Int) {
        val temp = puzzlePieces[index1]
        puzzlePieces[index1] = puzzlePieces[index2]
        puzzlePieces[index2] = temp

        puzzleGrid.removeAllViews()
        for (piece in puzzlePieces) {
            puzzleGrid.addView(piece)
        }

        emptyCellIndex = index1

        if (isPuzzleSolved()) {
            showPuzzleSolved()
        }
    }

    /*private fun isPuzzleSolved(): Boolean {
        for (i in puzzleImages.indices) {
            val imageView = puzzlePieces[i]
            val originalPosition  = imageView.tag as? Int ?: return false
            if (originalPosition != i) {
                return false
            }
        }
        return true
    }*/

    private fun isPuzzleSolved(): Boolean {
        for (i in puzzlePieces.indices) {
            val imageView = puzzlePieces[i]
            val drawable = imageView.drawable
            val expectedDrawable = ContextCompat.getDrawable(this, puzzleImages[i])
            if (drawable.constantState != expectedDrawable?.constantState) {
                return false
            }
        }
        return true
    }

    /*private fun showPuzzleSolved() {
        puzzleGrid.removeAllViews()
        for (i in puzzleImages.indices) {
            val imageView = ImageView(this)
            imageView.setImageResource(solvedImages)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            puzzleGrid.addView(imageView)
        }
    }*/
    private fun showPuzzleSolved() {
        puzzleGrid.removeAllViews()
        val imageView = ImageView(this)
        imageView.setImageResource(solvedImages)
        imageView.layoutParams = GridLayout.LayoutParams().apply {
            width = GridLayout.LayoutParams.MATCH_PARENT
            height = GridLayout.LayoutParams.MATCH_PARENT
        }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        puzzleGrid.addView(imageView)
    }

}