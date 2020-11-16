import pt.isel.canvas.*
import kotlin.random.Random.Default.nextInt

data class Position(val x :Int, val y :Int)

data class Move(val dx :Int, val dy :Int)

operator fun Position.plus(move :Move) :Position = Position(x+move.dx, y+move.dy)

data class Ball(val pos:Position,val move:Move, val radius :Int =20, val color :Int = RED)

fun Canvas.drawBall(b :Ball) {
    erase()
    drawCircle(b.pos.x,b.pos.y,b.radius,b.color)
}

fun Ball.step(xLimit :Int, yLimit :Int) :Ball {
    val p = pos + move
    val (x,y) = pos
    val (dx,dy) = move
    return when {
        x !in radius .. xLimit-radius ->
            Ball(Position(x-dx,y), Move(-dx,dy), radius, color)
        y !in radius .. yLimit-radius ->
            Ball(Position(x,y-dy), Move(dx,-dy), radius, color)
        else ->
            Ball(p,move,radius, color)

    }
}

fun main() {
    onStart {
        val arena = Canvas(600,300,CYAN)
        var ball = Ball(Position(arena.width/2,arena.height/2),Move(4,4))
        arena.drawBall(ball)
        arena.onTimeProgress(10) {
            ball = ball.step(arena.width, arena.height)
            arena.drawBall(ball)
        }
        arena.onKeyPressed { ke ->
            ball = if (ke.char == 'v' || ke.char == 'V')
                Ball(ball.pos, Move(nextInt(-5, 6), nextInt(-5, 6)), ball.radius,ball.color)
            else
                Ball(ball.pos, ball.move,ball.radius, when(ke.char) {
                    'b' -> BLUE
                    'r' -> RED
                    'g' -> GREEN
                    'y' -> YELLOW
                    else -> BLACK
                } )
        }
    }
    onFinish {
        println("Bye")
    }
}