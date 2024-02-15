import java.util.Scanner

fun solve(nums: MutableList<Int>): Int {
    var ans : Int =0
    for(num in nums){
        ans=ans xor num
    }
    return ans
}

fun main(){
    var reader=Scanner(System.`in`)
    var arr : MutableList<Int> = mutableListOf()
    var size : Int
    print("Enter size of array: ")
    size=reader.nextInt()
    var element:Int
    for(i in 1..size){
        print("Enter Number: ")
        element=reader.nextInt()
        arr.add(element)
    }
    println(solve(arr))
}