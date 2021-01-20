import pt.isel.canvas.*

/**
 * Game's information.
 *
 * @property balls List of Balls. (Ball type)
 *
 * @property racket Game's racket.
 *
 * @property over
 */
data class Game(val area:Area,
                val balls:List<Ball>,
                val racket: Racket,
                val over:Boolean = false,
                val finish:Boolean = false)
/**
 * Draws the game's content.(Ball's counter, balls in game, racket, game over and finish letters.)
 *
 * @receiver Canvas.
 *
 * @param g Game to draw.
 */
fun Canvas.drawGame(g:Game){
    erase()
    if (g.over) drawText(RADIUS, LIVES_Y, "Game Over", RED, 20)
    if (g.finish) drawText(RACKET_X + RACKET_WIDTH*2, LIVES_Y, "Finish", YELLOW, 20)
    drawText(g.area.width/2, g.area.height, g.area.score.toString(), WHITE, 20)
    g.balls.forEach {ball -> drawBall(ball)}
    drawArea(g.area)
    drawRacket(g.racket)
}


/**
 * Adds one more ball to the game.
 *
 * @receiver The game
 *
 * @return Games's list of balls with one more ball.
 */
fun Game.newBall():Game {
    val newRacket = Racket(racket.x, racket.width, true)
    val newArea = Area(area.width, area.height, area.bricks, area.lives - 1, area.score)
    return when {
        (area.lives==0) -> Game (area, balls, racket, over = true, finish)
        over || finish -> this
        else -> Game(newArea,balls + Ball(racket.x+RACKET_WIDTH/2, RACKET_Y-RADIUS, 0, 0), newRacket)
    }
}
/**
 *  Moves every ball inside Game's list of balls,
 *  and removes from it, the balls that leave
 *  the window from it's bottom border.
 *
 *  @receiver The game.
 *
 *  @return A list of balls with the moved balls in game.
 */
fun Game.move():Game{
    if (racket.ballOn || over || finish ) return this
    val moveAndCollision = ballMoveAndCollide()
    val newScore = area.score + moveAndCollision.score
    val finishScore = newScore + area.lives * 10
    val newArea = Area(area.width, area.height, moveAndCollision.bricksList, area.lives, newScore)
    val finishArea = Area(area.width, area.height, moveAndCollision.bricksList, area.lives, finishScore)
    val ballFinish = emptyList<Ball>() + Ball(racket.x+ RACKET_WIDTH/2, RACKET_Y - RADIUS, 0, 0)
    return if(newArea.bricks.size == unbreakableBricks.size)
        Game(finishArea, ballFinish, racket, over, finish = true)
    else  Game(newArea, moveAndCollision.ballsList, racket, over, finish)

}

data class UpdateList(val ballsList:List<Ball>, val bricksList: List<Brick>, val score:Int=0)

fun Game.ballMoveAndCollide() :UpdateList {
    val movedBalls = balls.map { it.move(this) }.filter {it.y in 0..(area.height + 2 * RADIUS)}
    var finalBricks = area.bricks
    var score = 0
     val finalBalls = movedBalls.map { ball ->
        val brickCollision = ball.brickCollide(finalBricks)
        val newBricks = finalBricks.map{ brick ->
            val newBrick = Brick(brick.x,brick.y,brick.type,brick.hitCount+1)
            if(brick in brickCollision.bricks) {
                score += newBrick.type.points
                newBrick
            }
            else brick
        }
        finalBricks = newBricks.filter { brick -> brick.hitCount != brick.type.hits }
        //movedBalls = movedBalls.replace(ball,brickCollision.ball)
         brickCollision.ball
    }
    return UpdateList(finalBalls, finalBricks,score)
}

/**
 * * Moves the racket on x axis on mouse move. Moves the ball/S too if the ball/s is/are on the racket.
 *
 * @param mEvent Mouse Event
 *
 * @receiver The game
 *
 * @return The game with the moved racket and ball/s
 */
fun Game.moveRacket(mEvent:MouseEvent):Game{
    val newRacket = Racket(mEvent.x - RACKET_WIDTH / 2, racket.width, if(finish)true else racket.ballOn)
    val newBalls = emptyList<Ball>() + Ball(mEvent.x, RACKET_Y - RADIUS, 0, 0)
    val racketInArea:Boolean = mEvent.x in RACKET_WIDTH / 2..area.width - RACKET_WIDTH / 2
    return when{
        racketInArea && !racket.ballOn -> Game(area, balls, newRacket, over, finish)
        racketInArea && racket.ballOn  -> Game(area, newBalls, newRacket, over, finish)
        else                           -> this
    }
}

fun Game.throwBall():Game{
    val newRacket = Racket(racket.x, racket.width, false)
    val newBalls = balls.replace(balls[0], Ball(balls[0].x, balls[0].y, balls[0].dx, DELTA_Y))
    return if (over) this else Game(area, newBalls, newRacket, over, finish)
}

fun List<Ball>.replace(old: Ball, new: Ball) = map {if (it == old) new else it}
