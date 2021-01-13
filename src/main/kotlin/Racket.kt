import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

/**
 * Racket's information.
 *
 * @property x horizontal position
 *
 * @property width Racket's width
 */
data class Racket(val x:Int, val width:Int, val ballOn:Boolean)

/**
 * Main rectangle width.
 */
const val RACKET_WIDTH = 60
/**
 * Main rectangle height.
 */
const val RACKET_HEIGHT = 10

/**
 * Corner rectangle width and color,
 * and ball's displacement variation
 * if it hits the corner.
 */
const val CORNER_WIDTH = 10
const val CORNER_COLOR = 0xFF3333
const val CORNER_ACCEL = 3

/**
 * Intermediate rectangle width, color,
 * and ball's displacement variation
 * if it hits the intermediate part.
 */
const val INTERMEDIATE_WIDTH = 15
const val INTERMEDIATE_COLOR = 0xFF6666
const val INTERMEDIATE_ACCEL = 1

/**
 * Racket starting horizontal position.
 */
const val RACKET_X = WIDTH/2 - RACKET_WIDTH/2
/**
 * Racket vertical position.
 */
const val RACKET_Y = 550

/**
 * Starting Racket
 */
val startingRacket = Racket(RACKET_X, RACKET_WIDTH, true)

/**
 * Draws the racket.
 *
 * @receiver where it draws
 *
 * @param rket Racket to draw
 */
fun Canvas.drawRacket(rket:Racket) {
    /**
     * Draws the corner rectangles of the racket.
     */
    fun drawCorners() {
        drawRect(rket.x , RACKET_Y, CORNER_WIDTH, RACKET_HEIGHT / 2, CORNER_COLOR)
        drawRect(rket.x + (rket.width - CORNER_WIDTH)
            , RACKET_Y
            , CORNER_WIDTH
            , RACKET_HEIGHT / 2
            , CORNER_COLOR)
    }
    /**
     * Draws the intermediate rectangles of the racket.
     */
    fun drawIntermediate(){
        drawRect(rket.x + CORNER_WIDTH, RACKET_Y, INTERMEDIATE_WIDTH, RACKET_HEIGHT / 2, INTERMEDIATE_COLOR)
        drawRect(rket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
            , RACKET_Y
            , INTERMEDIATE_WIDTH
            , RACKET_HEIGHT / 2
            , INTERMEDIATE_COLOR)
    }

    drawRect(rket.x, RACKET_Y, rket.width, RACKET_HEIGHT, WHITE)
    drawCorners()
    drawIntermediate()
}

