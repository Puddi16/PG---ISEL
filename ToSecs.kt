
fun main() {
/*
	print("Horas? ")
	val h = readLine()!!.trim().toInt()
	*/
	val h = readInt("Horas")
	/*
	print("Minutos? ")
	val m = readLine()!!.trim().toInt()
	*/
	val m = readInt("Minutos")
	/*
	print("Segundos? ")
	val s = readLine()!!.trim().toInt()
	*/
	val s = readInt("Segundos")
	
	val secs = h*3600 + m*60 + s
	println("$h:$m:$s = $secs segundos.")
}