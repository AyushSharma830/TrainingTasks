import java.util.Scanner

fun main(){
    val reader=Scanner(System.`in`)
    val weights : MutableList<Int> = mutableListOf()
    var integer:Int
    while(true){
        print("Enter apple weight in gram (-1 to stop ) : ")
        integer=reader.nextInt()
        if(integer==-1) break
        weights.add(integer)
    }
//    for(weight in weights){
//        println(weight)
//    }

}