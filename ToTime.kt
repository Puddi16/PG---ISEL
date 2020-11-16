/*
fun readInt(quest :String) :Int {
	print("$quest? ")
	return readLine()!!.trim().toInt()
}

data class Time(val h:Int, val m:Int, val s:Int)

fun secsToTime(secs :Int) =
	Time(secs/3600,secs%3600/60,secs%60)
	*/
fun main() {
/*
	print("Segundos? ")
	val secs = readLine()!!.toInt()
*/
	val secs = readInt("Segundos")
	/*
	val h = secs / 3600
	val m = (secs - h*3600)/60
	val s = secs - h*3600 - m*60
	*/
	val tm = secsToTime(secs)
	
	println("$secs segundos = ${tm.h}:${tm.m}:${tm.s}")
}