
const val DEBUG = true

fun readInt(quest :String) :Int {
    print("$quest? ")
    return if (DEBUG) {
        val v = (1..10).random()
        println(v)
        v
    }
    else
        readLine()!!.trim().toInt()
}

fun doOperation(operator :Char, fx :(Int,Int)->Int) {
    val a = readInt("A")
    val b = readInt("B")
    val res = fx(a,b)
    println("$a $operator $b = $res")
}

fun main() {
    doOperation('+'){ a , b -> a+b}
    doOperation('-'){ a , b -> a-b}
    doOperation('x'){ a , b -> a*b}
}