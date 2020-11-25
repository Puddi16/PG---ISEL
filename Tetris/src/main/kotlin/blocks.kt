import pt.isel.canvas.*

/**
 * BLock information.
 *
 * Position([x],[y]) and [color].
 */
data class Block(val x :Int, val y :Int,val color :Int)

const val BLOCK_SIDE = 28
const val BORDER = 3    // Must be odd

/**
 * Function that draws the block.
 */
fun Canvas.drawBlock(b :Block) {
    val x = b.x*BLOCK_SIDE      // in pixels
    val y = b.y*BLOCK_SIDE      // in pixels
    drawRect(x,y, BLOCK_SIDE, BLOCK_SIDE, b.color)
    drawRect(x+BORDER/2,y+BORDER/2, BLOCK_SIDE-BORDER, BLOCK_SIDE-BORDER,0xAAAAAA, BORDER)
}


/**
 * Moves the block  by horizontal and vertical displacement.
 *
 * @param dx Horizontal offset
 * @param dy Vertical offset
 * @return Moved Block
 * @receiver Original Block
 */
fun Block.move(dx :Int, dy : Int,f:List<Block>) :Block? {
    val x = x + dx
    val y = y + dy
    return if ( x in GRID_X && y in GRID_Y && f.all {it.x != x || it.y != y} ) Block(x,y,color) else null
}

val COLORS = listOf(BLUE, RED, YELLOW, CYAN, MAGENTA, GREEN, 0xFF8000)
fun newBlock() = Block(GRID_WIDTH/2,0, COLORS.random())

fun Block.toFix(f :List<Block>) :Boolean {
    if (y == GRID_Y.last) return true
    f.forEach {
        if (it.y == y+1 && it.x == x) return true
    }
    return false
}