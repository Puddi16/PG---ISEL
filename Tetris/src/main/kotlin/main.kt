import pt.isel.canvas.*

/**
 * Grid width in blocks
 */
const val GRID_WIDTH = 10

/**
 * Grid height in blocks
 */
const val GRID_HEIGHT = 20

val GRID_X = 0 until GRID_WIDTH

val GRID_Y = 0 until GRID_HEIGHT

/**
 * Program entry point for a Tetris-like game.
 */
fun main() {
    onStart{
        val arena = Canvas(GRID_WIDTH*BLOCK_SIDE,GRID_HEIGHT*BLOCK_SIDE,BLACK)
        var game = Tetris( newBlock(), emptyList())
        arena.drawTetris(game)
        arena.onKeyPressed {ke ->
            val g :Tetris? = when(ke.code) {
                LEFT_CODE -> game.move(-1,0)
                RIGHT_CODE -> game.move(1,0)
                DOWN_CODE -> game.move(0,1)
                //UP_CODE -> blk.move(0,-1)
                else -> null
            }
            if (g != null) {
                game = g
                arena.drawTetris(game)
            }
        }
    }
    onFinish { }
}