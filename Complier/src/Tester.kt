import java.util.regex.Matcher
import java.util.regex.Pattern

fun main(args:Array<String>){
    //变量名称
    val varible = "[a-z][a-z|0-9]*"
    val pattern = Pattern.compile(varible)
    val matcher = pattern.matcher("x124234234234")

    //正则表达式匹配是贪心和懒惰的
    //匹配出来一个表达式后要检验 比如 会出现 /- 的情况...
    val op = "[-+*/][-+*/]*=*"
    //需要去除段落中的空格避免影响
    var testCase = "x= 1"
    testCase = testCase.replace(" ","")
    val whole = "([a-z][a-z|0-9]*)([-+*/]=*)([a-z][a-z|0-9]*)"
    val pattern2 = Pattern.compile(whole)
    val matcher2 = pattern2.matcher("x-=x")

    if(matcher2.find()){
        println(matcher2.group(0)  + "\n" + matcher2.group(1) + "\n"+matcher2.group(2) )
    }
}