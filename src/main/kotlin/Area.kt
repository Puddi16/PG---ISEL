import pt.isel.canvas.*


/**
 * Game's Area information.
 *
 * @property width game's window width in pixels.
 *
 * @property height game's window height in pixels.
 */
data class Area(val width:Int, val height:Int, val bricks: List<Brick>, val lives:Int, val score:Int)

/**
 * Window (Area) Dimensions
 */
const val WIDTH = 13 * BRICK_WIDTH
const val HEIGHT = 600

/**
 * Number of lives and lives
 * panel vertical position
 */
const val LIVES = 5
const val LIVES_Y = HEIGHT - 10

/**
 * Starting Area
 */
val startingArea = Area(WIDTH, HEIGHT, levelOneBricks, LIVES, 0)

/**
 * Draws the game's area (Lives and bricks)
 *
 * @receiver Where to draw
 *
 * @param a Game's area
 */
fun Canvas.drawArea(a:Area){
    a.bricks.forEach {b -> drawBrick(b)}
    (0 until a.lives).forEach {lifeIndex -> drawBall(Ball(3*(lifeIndex+1)*RADIUS, LIVES_Y, 0, 0))}
}