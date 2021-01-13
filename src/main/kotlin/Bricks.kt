import pt.isel.canvas.*

/**
 * Bricks width and height in pixels
 */
const val BRICK_WIDTH = 32
const val BRICK_HEIGHT = 15

/**
 * Bricks border width and height and thickness in pixels
 */
const val BORDER_WIDTH = BRICK_WIDTH -1
const val BORDER_HEIGHT = BRICK_HEIGHT -1
const val BORDER_THICK = 1

/**
 * Brick information.
 *
 * Position ([x],[y]) and [type].
 * @property x Horizontal brick position in bricks
 * @property y Vertical brick position in bricks
 * @property type Brick's type
 */
data class Brick(val x:Int, val y:Int, val type:Bricks, val hitCount:Int=0)

/**
 * Colors in hexadecimal
 */
const val ORANGE = 0xFFA500
const val GOLD = 0xFFD700
const val SILVER = 0xC0C0C0

/**
 * Brick types.
 *
 * @property color Brick Possible colors
 *
 * @property points Points that bricks add to the score when hit
 *
 * @property hits Hits that the bricks take to disappear
 * "-1" hits means that it won't disappear if hit
 */
enum class Bricks(val color: Int, val points:Int, val hits:Int){White(WHITE, 1,1), Orange(ORANGE, 2, 1),
    Cyan(CYAN, 3,1), Green(GREEN, 4, 1), Red(RED, 6,1 ), Blue(BLUE, 7,1),
    Magenta(MAGENTA, 8,1), Yellow(YELLOW, 9,1), Gold(GOLD, 0,-1), Silver(SILVER, 0, 2)
}

/**
 * Draws a Brick
 *
 * @receiver [Canvas] Where it draws
 *
 * @param b Brick to draw
 *
 */
fun Canvas.drawBrick(b:Brick){
    val x = b.x * BRICK_WIDTH
    val y = b.y * BRICK_HEIGHT
    drawRect(x, y, BRICK_WIDTH, BRICK_HEIGHT, b.type.color)
    drawRect(x+BORDER_THICK, y+BORDER_THICK, BORDER_WIDTH, BORDER_HEIGHT, BLACK, BORDER_THICK)
}

fun getBrickCollumn(xRange: IntRange, yRange:IntRange, l:List<Bricks>):List<Brick>{
    var brickList = emptyList<Brick>()
    var index = 0
    yRange.forEach {y->
        xRange.forEach {x->
            brickList = brickList +
                    when(l[index]){
                        Bricks.White -> Brick(x, y,Bricks.White)
                        Bricks.Gold-> Brick(x, y,Bricks.Gold)
                        Bricks.Magenta-> Brick(x, y,Bricks.Magenta)
                        Bricks.Red-> Brick(x, y,Bricks.Red)
                        Bricks.Silver-> Brick(x, y,Bricks.Silver)
                        Bricks.Blue-> Brick(x, y,Bricks.Blue)
                        Bricks.Green  -> Brick(x, y,Bricks.Green)
                        Bricks.Yellow -> Brick(x, y,Bricks.Yellow)
                        Bricks.Orange -> Brick(x, y,Bricks.Orange)
                        Bricks.Cyan -> Brick(x, y,Bricks.Cyan)
                    }
        }
        ++index
    }
    return brickList
}

val middleBrickLine = listOf(
    Bricks.Orange,
    Bricks.Cyan,
    Bricks.Green,
    Bricks.Red,
    Bricks.Blue,
    Bricks.Magenta,
    Bricks.Silver)


val leftAndRightLines = listOf(
    Bricks.Yellow,
    Bricks.Magenta,
    Bricks.Blue,
    Bricks.Red,
    Bricks.Green,
    Bricks.Cyan,
    Bricks.Orange,
    Bricks.White)


val middleCollumnFirstLine = listOf<Brick>(
    Brick(5, 3, Bricks.White), Brick(6, 3, Bricks.Gold), Brick(7, 3, Bricks.White))


val middleBrickFullLines = getBrickCollumn(5..7, 4..10, middleBrickLine) + middleCollumnFirstLine


val levelOneBricks:List<Brick> = getBrickCollumn(1..3,3..10, leftAndRightLines) +
        getBrickCollumn(9..11, 3..10, leftAndRightLines) + middleBrickFullLines

val unbreakeableBricks:List<Brick> = levelOneBricks.filter {b-> b.type.hits == -1}