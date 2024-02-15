import java.util.Scanner
import java.util.Stack

fun validParanthesis(input : String) : Boolean{
    val st= Stack<Int>()
    for(index in 0..input.length-1){
        if(input[index]=='(' || input[index]=='[' || input[index]=='{' )    st.push(index)
        else if(input[index]==')'){
            if(!st.isEmpty() && input[st.peek()]=='(')  st.pop()
            else    return false
        }
        else if(input[index]==']'){
            if(!st.isEmpty() && input[st.peek()]=='[')  st.pop()
            else    return false
        }
        else if(input[index]=='}'){
            if(!st.isEmpty() && input[st.peek()]=='{')  st.pop()
            else    return false
        }
    }
    if(st.isEmpty()) return true
    return false
}

fun main(){
    var reader = Scanner(System.`in`)
    print("Enter Expression: ")
    var input : String = reader.nextLine()
    println(validParanthesis(input))
}