import java.util.Scanner

fun distributeApples(index : Int, arr : MutableList<Int>, total : Int, ans : MutableList<Int>) :  {
    if(target == 0) return 0
    if(index == arr.size()) return -1
    var notTake : Int = distributeApples(index+1,arr,total,ans)
    var take : Int =0
    if(arr[index]<=total) {
        ans.push_back(arr[index])
        take = distributeApples(index + 1, arr, total - arr[index], ans)
    }
    return max(take,notTake)
}

fun main(){
    val reader=Scanner(System.`in`)
    val weights : MutableList<Int> = mutableListOf()
    var integer:Int
    var totWeight:Int = 0
    while(true){
        print("Enter apple weight in gram (-1 to stop ) : ")
        integer=reader.nextInt()
        if(integer==-1) break
        weights.add(integer)
        totWeight+=integer
    }
//    for(weight in weights){
//        println(weight)
//    }
    println("Distribution Result: ")
    var ramShare:Int = totWeight/2
    var ramApples:MutableList<Int> = mutableListOf()
    distributeApples(0,weights,ramShare, ramApples)
    print("Ram : ")
    for (it in ramApples){
        print("$it ,")
        weights.remove(it)
    }
}