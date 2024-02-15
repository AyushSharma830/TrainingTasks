import java.util.Scanner

fun addBinary(a: String, b: String): String {
    var ans : String=""
    var i : Int = a.length-1
    var j : Int = b.length-1
    var carry : Int = 0
    while(i>=0 || j>=0 || carry==1){
        if(i>=0){
            carry=carry+(a[i]-'0')
            i--
        }
        if(j>=0){
            carry=carry+(b[j]-'0')
            j--
        }
        ans=ans+(carry%2)
        carry=carry/2
    }
    var finalAns : String = ans.reversed()
    return finalAns
}

fun main(){
    var reader = Scanner(System.`in`)
    print("Enter String a: ")
    var a :String=reader.nextLine()
    print("Enter String b: ")
    var b :String=reader.nextLine()
    println(addBinary(a,b))
}