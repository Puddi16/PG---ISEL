@file:Suppress("SpellCheckingInspection")

import pt.isel.canvas.*

data class Smile(val x :Int,val y :Int, val radius :Int =50)

const val delta = 4  // DESLOCAMENTO DO SMILE
const val Radius = 50   // RAIO DO SMILE

const val Width = 600   // LARG URA DO CANVAS
const val Height = 300  // ALTURA DO CANVAS

const val EYES_FACTOR = 0.35    //FATOR QUE DÁ A POSIÇÃO DO OLHO
const val EYE_RADIUS = 0.125    //FATOR QUE DÁ O TAMANHO DO OLHO
const val THICKNESS_FACTOR = 0.07   //FATOR QUE DÁ A ESPESSURA DA BOCA E DO CONTORNO

fun Canvas.drawEyes(smile :Smile) {

    drawCircle(smile.x - (smile.radius* EYES_FACTOR).toInt(),
            smile.y - (smile.radius* EYES_FACTOR).toInt(),
            (smile.radius* EYE_RADIUS).toInt(), BLACK)    // DESENHAR O OLHO ESQUERDO

    drawCircle(smile.x + (smile.radius* EYES_FACTOR).toInt(),
            smile.y - (smile.radius* EYES_FACTOR).toInt(),
            (smile.radius* EYE_RADIUS).toInt(), BLACK)    // DESENHAR O OLHO DIREITO
}
// DESENHAR O SMILE
fun Canvas.drawSmile(smile :Smile) {
    erase()

    drawCircle(smile.x, smile.y, smile.radius, BLACK)   // DESENHAR O CONTORNO
    
    drawCircle(smile.x, smile.y, (smile.radius*(1- THICKNESS_FACTOR)).toInt(), YELLOW)   // DESENHAR O SMILE AMARELO

    drawEyes(smile)
    drawArc(smile.x, smile.y,
        (smile.radius*0.6).toInt(), 200, 340, BLACK,
        (smile.radius* THICKNESS_FACTOR).toInt())    // DESENHAR A BOCA
}

//MOVER O SMILE USANDO AS SETAS
fun Smile.move(keyCode :Int) :Smile {
    return when (keyCode) {
        //MOVER O SMILE PARA CIMA
        UP_CODE -> Smile(x,y-delta,radius)
        //MOVER O SMILE PARA A ESQUERDA
        LEFT_CODE -> Smile(x-delta,y,radius)
        //MOVER O SMILE PARA BAIXO
        DOWN_CODE -> Smile(x,y+delta,radius)
        //MOVER O SMILE PARA A DIREITA
        RIGHT_CODE -> Smile(x+delta,y,radius)

        else -> this
    }
}

/**AUMENTAR OU DIMINUIR O TAMANHO DO SMILE*/
fun Smile.resize(keyChar :Char) :Smile {
    return when (keyChar) {
        //AUMENTAR O RAIO DO SMILE
        '+' -> {
            val rad = radius + 1
            Smile(x,y,rad)
        }
        //DIMINUIR O RAIO DO SMILE
        '-' -> {
            val rad = if ((radius - 1) * 0.7 >= 1 )
            radius - 1
            else radius
            Smile(x,y,rad)
        }
        else -> this
    }
}

fun main() {
    onStart {
        val arena = Canvas(Width,Height,CYAN)
        var smile = Smile(arena.width/2,arena.height/2,Radius)
        arena.drawSmile(smile)

        arena.onMouseDown { mEvent ->
            smile = Smile(mEvent.x,mEvent.y,smile.radius)
            arena.drawSmile(smile)
        }
        arena.onKeyPressed { ke ->
            smile = if(ke.char in "+-") smile.resize(ke.char)
            else smile.move(ke.code)

            arena.drawSmile(smile)
        }
    }
    onFinish{
        println("Done")
    }
}
