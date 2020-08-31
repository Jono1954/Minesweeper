package minesweeper

const val SYMBOL_UNEXPLORED = "."
const val SYMBOL_EXPLORED = "/"
const val SYMBOL_MINE = "X"
const val SYMBOL_STAR = "*"

const val NO_OF_ROWS = 9
const val NO_OF_COLS = 9
const val START_NUM = 0

data class Cell(
        var id: Int = 0,
        var row: Int = 0,
        var col: Int = 0,
        var symbol: String = SYMBOL_UNEXPLORED,
        var isExplored: Boolean = false,
        var hasMine: Boolean = false,
        var isMarked: Boolean = false,
        var minesRoundCell: Int = 0
)

data class Input(var x: String = "", var y: String = "", var choice: String = "") // user input
data class Converted(var row: Int = 0, var col: Int = 0, var choice: String = "") // user input converted to our row/col grid

var randomListOfMineCellsIds = mutableListOf<Int>()
var listOfMineCells = mutableListOf<Cell>()
var choice = ""         // user input - either 'free' or 'mine'


fun main(args: Array<String>) {

    var noOfMines = getNumberOfMinesFromUser()

    // set up the minefield
    val minefield =
            setUpMinefield().apply {
                setMinesInRandomPositions(noOfMines, this)
                getMinesRoundEmptyCells(this)
                printMinefield(this, true)
            }

    var isFirstFree = true  // first time user types in 'free' choice

    var repeat = true
    do {
        getInputFromUser().apply {
            convertUserInputXYtoRowCol(this).apply {
                getCellChosenByUser(this, minefield).apply {

                    when (choice) {

                        "free" -> {
                            when {

                                // Move mine to new cell
                                this.hasMine && isFirstFree -> {
                                    moveMineToAnotherRandomCell(this, minefield)
                                    getMinesRoundEmptyCells(minefield)
                                    printMinefield(minefield, true)
                                    isFirstFree = false
                                    repeat = true
                                }

                                // end game
                                this.hasMine -> {
                                    println("You stepped on a mine and failed!")
                                    printMinefield(minefield, false)
                                    repeat = false
                                }

                                // cell has no mines round
                                this.minesRoundCell == 0 -> {   // no mines in adjacent cells
                                    this.autoExplore(minefield)
                                    printMinefield(minefield, true)
                                }

                                // cell has mines round
                                this.minesRoundCell > 0 -> {
                                    this.symbol = this.minesRoundCell.toString()
                                    printMinefield(minefield, true)
                                }

                                // cell unexplored, mark with star
                                this.symbol == SYMBOL_UNEXPLORED -> {
                                    this.symbol = SYMBOL_STAR
                                    this.isMarked = true
                                    printMinefield(minefield, true)
                                }

                                // cell marked with star, remove star, mark as unexplored
                                this.symbol == SYMBOL_STAR -> {
                                    this.symbol = SYMBOL_UNEXPLORED
                                    this.isMarked = false
                                    printMinefield(minefield, true)
                                }
                            }
                        }

                        "mine" -> {
                            when {

                                // cell marked with star, remove star, mark as unexplored
                                this.symbol == SYMBOL_STAR -> {
                                    this.symbol = SYMBOL_UNEXPLORED
                                    this.isMarked = false
                                    printMinefield(minefield, true)
                                }

                                // mark cell with star
                                else -> {
                                    this.symbol = SYMBOL_STAR
                                    this.isMarked = true
                                    printMinefield(minefield, true)
                                }
                            }
                        }
                    }
                }
            }
        }

        if (checkGameOver(minefield)) {
            println("Congratulations! You found all the mines!")
            repeat = false
        }
    } while (repeat)
}

private fun Cell.autoExplore(minefield: Array<Array<Cell>>) {
    this.symbol = SYMBOL_EXPLORED
    this.isExplored = true

    val currentCell = this

    var col = currentCell.col
    var row = currentCell.row
    while (--col in 0 until NO_OF_COLS && minefield[row][col].minesRoundCell == 0) {
        getValidAdjacentCellPositions(minefield[row][col], minefield)
                .forEach {
                    it.symbol = SYMBOL_EXPLORED
                    it.isExplored = true
                }
    }   // end while

    col = currentCell.col
    row = currentCell.row
    while (++col in 0 until NO_OF_COLS && minefield[row][col].minesRoundCell == 0) {
        getValidAdjacentCellPositions(minefield[row][col], minefield)
                .forEach {
                    it.symbol = SYMBOL_EXPLORED
                    it.isExplored = true
                }
    }   // end while

    col = currentCell.col
    row = currentCell.row
    while (--row in 0 until NO_OF_ROWS && minefield[row][col].minesRoundCell == 0) {
        getValidAdjacentCellPositions(minefield[row][col], minefield)
                .forEach {
                    it.symbol = SYMBOL_EXPLORED
                    it.isExplored = true
                }
    }   // end while


    col = currentCell.col
    row = currentCell.row
    while (++row in 0 until NO_OF_ROWS && minefield[row][col].minesRoundCell == 0) {
        getValidAdjacentCellPositions(minefield[row][col], minefield)
                .forEach {
                    it.symbol = SYMBOL_EXPLORED
                    it.isExplored = true
                }
    }   // end while
}


// *************************************************************************************************

private fun getNumberOfMinesFromUser(): Int {
    print("How many mines do you want on the field? ")
    return readLine()!!.split(" ").first().toInt()
}

private fun setUpMinefield(): Array<Array<Cell>> {
    // Initialise every minefield position to a default 'Cell' object
    var count = 0
    return Array(NO_OF_ROWS) { row: Int ->
        Array(NO_OF_COLS) { col: Int ->
            Cell(id = count++, row = row, col = col)
        }
    }
}

private fun setMinesInRandomPositions(numOfMinesSpecified: Int, minefield: Array<Array<Cell>>) {
    (0 until NO_OF_ROWS * NO_OF_COLS).shuffled().take(numOfMinesSpecified)
            .also { randomListOfMineCellsIds = it.toMutableList() }
    for (id in randomListOfMineCellsIds) {
        val row = id / NO_OF_COLS // integer division gives the row position
        val col = (id % NO_OF_COLS) // remainder gives the column position
        minefield[row][col].hasMine = true
        minefield[row][col].symbol = SYMBOL_MINE
    }
}

private fun getMinesRoundEmptyCells(minefield: Array<Array<Cell>>) {
    // Check every cell on minefield for surrounding mines
    for (row in 0 until NO_OF_ROWS) {
        for (col in 0 until NO_OF_COLS) {
            val cell = minefield[row][col]

            cell.apply {
                // if cell being checked doesn't have a mine
                if (!this.hasMine) {

                    // Get number of surrounding cells with mines
                    var noOfMines = 0

                    val validCells = getValidAdjacentCellPositions(this, minefield)

                    for (cellRound in validCells) {
                        if (cellRound.hasMine) noOfMines++
                    }

                    // Assign total number of adjacent mines to empty cell
                    if (noOfMines >= 0) {   // assign zero to cell if no mines
                        this.symbol = noOfMines.toString()
                        this.minesRoundCell = noOfMines
                    }
                }
            }
        }
    }
}

private fun getValidAdjacentCellPositions(currentCell: Cell, minefield: Array<Array<Cell>>): List<Cell> {
    val row = currentCell.row
    val col = currentCell.col
    val allPossibleAdjacentCellPositions =
            listOf(
                    Cell((row - 1) * NO_OF_COLS + col, row - 1, col),                   // north
                    Cell((row - 1) * NO_OF_COLS + col + 1, row - 1, col + 1),       // north-east
                    Cell(row * NO_OF_COLS + col + 1, row, col + 1),                     // east
                    Cell((row + 1) * NO_OF_COLS + col + 1, row + 1, col + 1),       // south-east
                    Cell((row + 1) * NO_OF_COLS + col, row + 1, col),                   // south
                    Cell((row + 1) * NO_OF_COLS + col - 1, row + 1, col - 1),       // south-west
                    Cell(row * NO_OF_COLS + col - 1, row, col - 1),                     // west
                    Cell((row - 1) * NO_OF_COLS + col - 1, row - 1, col - 1)        // north-west
            )

    // Filter out invalid cell positions that fall outside the minefield
    val validListOfAdjacentCells = mutableListOf<Cell>()
    for (cellRound in allPossibleAdjacentCellPositions) {
        if (cellRound.row in 0 until NO_OF_ROWS && cellRound.col in 0 until NO_OF_COLS) {
            validListOfAdjacentCells.add(getCellUsingRowCol(cellRound.row, cellRound.col, minefield))
        }
    }
    return validListOfAdjacentCells.toList()
}

private fun printMinefield(minefield: Array<Array<Cell>>, hideMines: Boolean) {
    println()
    println(" │123456789│")
    println("—│—————————│")

    for (i in 0 until NO_OF_ROWS) {
        print("${i + 1}│")
        for (j in 0 until NO_OF_COLS) {
            val cell = minefield[i][j]
            if (hideMines) {   // true - hide mines
                when {
                    cell.hasMine && cell.isMarked -> print(SYMBOL_STAR)
                    cell.hasMine && !cell.isMarked -> print(SYMBOL_UNEXPLORED)
                    else -> print(cell.symbol)
                }
            } else {    // false - show mines
                when {
                    cell.hasMine && cell.isMarked -> print(SYMBOL_STAR)
                    cell.hasMine && !cell.isMarked -> print(SYMBOL_MINE)
                    else -> print(cell.symbol)
                }
            }
        }
        print("│")
        println()
    }
    println("—│—————————│")
}

// *************************************************************************************************

private fun getInputFromUser(): Input {
    print("Set/unset mines marks or claim a cell as free ")
    readLine()!!.split(" ").apply {
        choice = this[2]
        return Input(x = this[0], y = this[1], choice = this[2])
    }
}

private fun convertUserInputXYtoRowCol(input: Input): Converted {
    return Converted(row = input.y.toInt() - 1, col = input.x.toInt() - 1, choice = input.choice)
}

private fun getCellChosenByUser(converted: Converted, minefield: Array<Array<Cell>>): Cell {
    return minefield[converted.row][converted.col]
}

// *************************************************************************************************

private fun getCellUsingCellId(id: Int, minefield: Array<Array<Cell>>): Cell {
    val row = id / NO_OF_COLS // integer division gives the row position
    val col = id % NO_OF_COLS // remainder gives the column position
    return minefield[row][col]
}

private fun getCellUsingRowCol(row: Int, col: Int, minefield: Array<Array<Cell>>): Cell {
    return minefield[row][col]
}

// private fun getCellUsingXY(x: Int, y: Int, minefield: Array<Array<Cell>>): Cell {
//     return minefield[y - 1][x - 1]
// }
//
// private fun computeCellIdUsingRowCol(row: Int, col: Int): Int {
//     return row * NO_OF_COLS + col   // cell ID same as minefield index
// }
//
// private fun computeCellIdUsingXY(x: Int, y: Int): Int {
//     return (y - 1) * NO_OF_COLS + (x - 1)  // cell ID same as minefield index
// }

// *************************************************************************************************


private fun moveMineToAnotherRandomCell(discardedMineCell: Cell, minefield: Array<Array<Cell>>): Cell {
    var findAnotherMineCell = true
    while (findAnotherMineCell) {
        // Get a new random cell location on the minefield
        (0 until NO_OF_ROWS * NO_OF_COLS).random().apply {
            getCellUsingCellId(this, minefield).apply {
                if (!hasMine) {    // new random cell doesn't already have a mine
                    hasMine = true
                    listOfMineCells.add(this)   // add new mine cell to list
                    findAnotherMineCell = false
                }
            }
        }
    }

    // Now remove discarded mine cell from list of mine cells
    discardedMineCell.apply {
        listOfMineCells.remove(this)
        hasMine = false
        symbol = SYMBOL_EXPLORED
        isExplored = true
    }
    return listOfMineCells.last()
}

private fun checkGameOver(minefield: Array<Array<Cell>>): Boolean {
    // Two tests for game over
    // 1. All mines are marked with a '*' (SYMBOL_STAR)
    for (cell in listOfMineCells) {
        if (cell.hasMine && !cell.isMarked) return false
    }

    // 2. All cells have been explored except for those cells with mines
    println("Second test - all cells marked with '/' except for cells with mines")

    for (cell in getAListOfAllCells(minefield)) {
        if (!cell.hasMine && !cell.isExplored) return false
    }

    println()
    println("Game is over.")
    return true
}

private fun getAListOfAllCells(minefield: Array<Array<Cell>>): MutableList<Cell> {
    return mutableListOf<Cell>().apply {
        for (i in 0 until NO_OF_ROWS) {
            for (j in 0 until NO_OF_COLS) {
                this.add(minefield[i][j])
            }
        }
    }
}