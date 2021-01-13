import pt.isel.canvas.*

/**
 * Program entry point for MultiBall.
 *
 * Moves the racket on mouse move.
 * Adds one ball to the game every 5 seconds.
 * Moves every ball in the game, and bounces the ball
 * if it hits the racket or a border of the game's area.
 * Closes the window when every ball leaves game's area.
 */
fun main() {
    onStart{
        var game = Game(startingArea, startingBalls, startingRacket)
        val arena = Canvas(game.area.width, game.area.height, BLACK)
        arena.drawGame(game)
        arena.onMouseMove { me ->
            game = game.moveRacket(me)
            arena.drawGame(game)
        }
        arena.onMouseDown {
            game = when{
                game.balls.isEmpty() -> game.newBall()
                game.racket.ballOn   -> game.throwBall()
                else                 -> game
            }
        }
        arena.onTimeProgress(10) {
            game = game.move()
            arena.drawGame(game)
        }
    }
    onFinish {
        println("Game Over")
    }
}
