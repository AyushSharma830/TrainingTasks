import java.util.Scanner
import kotlin.math.abs

fun reverseNumber(x: Int): Int {
    var number :Int =abs(x)
    var reversedNumber : Long = 0
    var digit : Int
    val max=Int.MAX_VALUE
    val min=Int.MIN_VALUE
    while(number>0){
        digit = number%10
        reversedNumber = reversedNumber *10 + digit
        number=number/10
    }
    if(x<0) reversedNumber=-1*reversedNumber
    if(reversedNumber> max || reversedNumber< min )
        return 0
    var ans : Int =reversedNumber.toInt()
    return ans
}

fun main(){
    var reader=Scanner(System.`in`)
    var number : Int
    print("Enter Number: ")
    number=reader.nextInt()
    println(reverseNumber(number))
}