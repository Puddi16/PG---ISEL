import pt.isel.canvas.*

data class Tetris(val moving :Block, val fixed :List<Block>)

fun Canvas.drawTetris(g :Tetris) {
    erase()
    drawBlock(g.moving)
    g.fixed.forEach (::drawBlock)
}

fun Tetris.move (dx :Int, dy :Int) :Tetris? {
    val b = moving.move(dx,dy,fixed)
    return when{
        b == null           -> null
        b.toFix( fixed )    -> fixBlock(b)
        else                -> Tetris(b,fixed)
    }
}

fun Tetris.fixBlock(b: Block) :Tetris {
    val f = fixed + b
    //TODO: Verificar linhas completas
    return Tetris(newBlock(), fixed + b)
}
