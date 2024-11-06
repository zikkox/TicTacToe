package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gridLayout: GridLayout

    //creates a 2d array based on size of rows and columns
    //position of each button is added and stored in the 2d array (grid)
    private val rows = 3
    private val columns = 3
    private val grid = Array(rows) { arrayOfNulls<Button>(columns) }

    private var xTurn: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticTacToe()
    }

    private fun winCheck(turn: String, posX: Int, posY: Int) {

        var rowWin: Boolean = true
        var columnWin: Boolean = true
        var draw: Boolean = true

        //check for row (horizontal) win
        for (column in 0 until columns) {
            if (grid[posX][column]?.text != turn) {
                rowWin = false
                break
            }
        }

        //check for column (vertical) win
        for (row in 0 until rows) {
            if (grid[row][posY]?.text != turn) {
                columnWin = false
                break
            }
        }

        //check for draw
        for (column in 0 until columns) {
            for (row in 0 until rows) {
                if (grid[row][column]?.text == "") {
                    draw = false
                }
            }
        }

        if (columnWin ||
            rowWin
        ) {
            Toast.makeText(this, "$turn won", Toast.LENGTH_SHORT).show()
            resetGame()
        } else if (draw) {
            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
            resetGame()
        }
    }

    private fun ticTacToe() {

        //sets the grid size and adds buttons
        gridLayout = binding.gridLayout

        gridLayout.removeAllViews()
        gridLayout.rowCount = rows
        gridLayout.columnCount = columns

        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val newButton = Button(this).apply {
                    text = ""
                    textSize = 20f
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        rowSpec = GridLayout.spec(row, 1f)
                        columnSpec = GridLayout.spec(column, 1f)
                        setMargins(8, 8, 8, 8)
                    }
                }
                gridLayout.addView(newButton)
                grid[row][column] = newButton

                //displays X or O based on turn
                //checks for win accordingly
                newButton.setOnClickListener() {
                    if (newButton.text != "X" &&
                        newButton.text != "O"
                    ) {
                        if (xTurn) {
                            newButton.text = "X"
                            winCheck("X", row, column)
                        } else {
                            newButton.text = "O"
                            winCheck("O", row, column)
                        }
                        xTurn = !xTurn
                    }
                }
            }
        }
    }

    //reloads activity
    private fun resetGame() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}