fun readInt( quest :String ) :Int {
    print("$quest ")
    return readLine()!!trim().toInt()
}
fun main() {
    print("Valor? ")
    val a = readInt("1º Valor")

    print("Valor? ")
    val b = readInt("2º Valor")

    print ("Valor? ")
    val c = readInt("3º Valor")

    println(${(a+b+c)/3})
}