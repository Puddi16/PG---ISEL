import pt.isel.canvas.*
import kotlin.math.sqrt

/**
 * Ball's information.
 *
 * @property x horizontal coordinate.
 *
 * @property y vertical coordinate.
 *
 * @property dx horizontal coordinate variation.
 *
 * @property dy vertical coordinate variation.
 */
data class Ball(val x :Int, val y :Int, val dx :Int, val dy :Int)
/**
 * Ball radius.
 */
const val RADIUS = 7

/**
 * Ball vertical moving velocity.
 */
const val DELTA_Y = -4

/**
 * Ball horizontal moving velocity.
 */
val DELTA_X = (-6..6)

val startingBalls = emptyList<Ball>() + Ball(RACKET_X + RACKET_WIDTH / 2,RACKET_Y - RADIUS,0,0)

/**
 *  Draws a ball.
 *
 *  @param b :Ball
 */
fun Canvas.drawBall(b :Ball){
    drawCircle(b.x, b.y, RADIUS, CYAN)
}

/**
 * Limits Ball horizontal movement to DELTA_X interval.
 *
 * @return [value] limited to DELTA_X.
 */
fun limitTo(value:Int, range :IntRange) = when {
    value in range   -> value
    value>range.last -> range.last
    else              -> range.first
}

/**
 * Moves a ball based on it's movement and makes it bounce on window borders and racket.
 *
 * @param g  The game.
 *
 * @receiver A ball.
 *
 * @return A moved ball.
 */
fun Ball.move(g:Game) :Ball{
    val newX = x + dx
    val newY = y + dy
    val racketEndX = g.racket.x + RACKET_WIDTH
    val racketHit = (newY + RADIUS in RACKET_Y..RACKET_Y + RACKET_HEIGHT && this.dy > 0
            && (newX + RADIUS in g.racket.x..racketEndX ||newX - RADIUS in g.racket.x..racketEndX ))

    /**
     * Checks if the ball collides with the racket, and alters the ball's movement
     * based on which part of the racket the ball collided with.
     *
     * @receiver A ball
     *
     * @return The same ball with it's movement altered.
     */
    fun collision() :Ball{
        // Different racket parts.
        val center = g.racket.x + CORNER_WIDTH + INTERMEDIATE_WIDTH
        val rightInter = g.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
        val leftInter = g.racket.x + CORNER_WIDTH
        val rightCorner = g.racket.x + RACKET_WIDTH - CORNER_WIDTH
        //  Collision conditions.
        val rightInterHit = newX in rightInter until rightCorner
        val leftInterHit = newX in leftInter until center
        val rightCornerHit = newX in rightCorner..racketEndX || newX - RADIUS in rightCorner..racketEndX
        val leftCornerHit = newX in g.racket.x until leftInter || newX + RADIUS in g.racket.x until leftInter
        return Ball(x, y,
            when {
                rightCornerHit  -> limitTo(dx + CORNER_ACCEL, DELTA_X)
                leftCornerHit   -> limitTo(dx - CORNER_ACCEL, DELTA_X)
                leftInterHit    -> limitTo(dx - INTERMEDIATE_ACCEL, DELTA_X)
                rightInterHit   -> limitTo(dx + INTERMEDIATE_ACCEL, DELTA_X)
                else -> dx
            }, -dy)
    }
    return if (racketHit) collision()
    else when {
        newX !in 0 + RADIUS..g.area.width - RADIUS  -> Ball(x, newY, -dx, dy)
        newY < 0 + RADIUS                           -> Ball(newX, y, dx, -dy)
        else                                        -> Ball(newX, newY, dx, dy)
    }
}

data class BrickCollision(val ball: Ball, val bricks :List<Brick>)

/**
 * Calculates the distance between two coordinates in pixels
 */
fun distance(coord1X:Int, coord1Y:Int, coord2X:Int, coord2Y:Int):Float{
    val xDistance: Int = coord1X - coord2X
    val yDistance: Int = coord1Y - coord2Y
    return sqrt((xDistance.square() + yDistance.square()).toFloat())
}
/**
 * @receiver An Integer
 *
 * @return The integer power 2
 */
fun Int.square() = this * this

data class Velocity(val dx :Int, val dy :Int)
// Change forEach
fun Ball.brickCollide(bricks: List<Brick>):BrickCollision{
    var finalVelocity = Velocity(this.dx,this.dy)
    var finalBricks = emptyList<Brick>()
    bricks.forEach{ brick ->
        val xBrick = brick.x * BRICK_WIDTH
        val yBrick = brick.y * BRICK_HEIGHT
        val closestX = limitTo(this.x, xBrick..xBrick+BRICK_WIDTH)
        val closestY = limitTo(this.y, yBrick..yBrick+BRICK_HEIGHT)
        val collided = distance(this.x, this.y, closestX, closestY) <= RADIUS
        if(collided){
            if(closestX == xBrick && this.dx > 0 || closestX == xBrick + BRICK_WIDTH && this.dx < 0) {
                finalBricks = finalBricks + brick
                finalVelocity = Velocity(-this.dx, this.dy)
            }
            else if(closestY == yBrick && this.dy > 0 || closestY == yBrick + BRICK_HEIGHT && this.dy < 0) {
                finalBricks = finalBricks + brick
                finalVelocity = Velocity(this.dx, -this.dy)
            }
        }
    }
    return BrickCollision(Ball(x, y, finalVelocity.dx, finalVelocity.dy), finalBricks)
}