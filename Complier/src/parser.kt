import java.lang.IllegalArgumentException
import java.util.regex.Pattern

const val testCase1=  "begin x :=9; if x > 0 then x := 2*x + 1/3; end #"
const val testCase2=  "begin x := 10; y=x; end #"
const val testCase3 = "begin if x==1 then x := 2"
val result = ArrayList<Pair<String,Int>>()

fun parse(sourceString :String){
    initTable()

    //先将这个表达式大体分开
    val array = sourceString.split(" ")
    val keySet = table.keys

    for(i in array){
        //是一个关键字
        when {
            i in keySet -> result.add(Pair(first = i,second = table[i]!!))
        //可能是一个标示符
            isIdentifier(i) -> result.add(Pair(first = i,second = 10))
            isNumber(i) -> result.add(Pair(first = i,second = 11))
            else ->
                //只可能是一个表达式
                //或者是一个block
                parseDetail2(i)
        }
    }
}

fun isNumber(source:String):Boolean{
    if(source[0] in 'a' .. 'z' || source[0] in 'A' .. 'Z')
        return false

    var parse = source
    if(source.startsWith("0x") || source.startsWith("0X"))
        parse  = parse.substring(2,source.length)

    parse.forEach {
        //todo char 类型的转换的结果
        val i = it.toInt() - 48
        if(i !in 0 .. 9){
            return false
        }
    }
    return true
}
fun isIdentifier(source:String):Boolean{
    var flag = false
    if(source[0] in 'a' ..'z' || source[0] in 'A' .. 'Z' ){
        if(source.length == 1)
            return true

        source.substring(1,source.length).toCharArray().forEach {
            //todo 这里可能会有bug
            if(it !in 'a' .. 'z' || it !in 'A'.. 'Z' || it.toInt() !in 0 .. 9 ){
                return false
            }
        }
        flag =true
    }
    return flag
}

private fun parseDetail(source: String) {
    var i = 0
    while (i < source.length) {
        when (source[i]) {
        //todo 判断i+1是否可行private fun parseDetail(testCase1: String)
            '<' ->
                if (i + 1 < source.length && source[i + 1] == '=') {
                    //向result 中添加 <=
                    result.add(Pair("<=", table["<="]!!))
                    i += 2
                } else {
                    result.add(Pair("<", table["<"]!!))
                }

            '>' ->
                if (i + 1 < source.length && source[i + 1] == '=') {
                    //添加 >=
                    //向result 中添加 <=
                    result.add(Pair(">=", table[">="]!!))
                    i += 2
                } else {
                    result.add(Pair(">", table[">"]!!))
                }

            '=' ->
                if (i + 1 < source.length && source[i + 1] == '=') {
                    //添加 ==
                    result.add(Pair("==", table["=="]!!))
                    i += 2
                } else {
                    result.add(Pair("=", table["="]!!))
                    i++
                }
            '!' ->
                if (i + 1 < source.length && source[i + 1] == '=') {
                    //添加！=
                    result.add(Pair("!=", table["!="]!!))
                    i += 2
                } else {
                    throw IllegalArgumentException("没有这样的运算符${source[i]}")
                }
            ':' -> if (i + 1 < source.length && source[i + 1] == '=') {
                //添加 ：=
                //向result 中添加 <=
                result.add(Pair(":=", table[":="]!!))
                i += 2
            }
            ';' ->{
                result.add(Pair(";",table[";"]!!))
                i++
            }
            '#' ->{
                result.add(Pair("#",table["#"]!!))
                i++
            }
            '*' ->{
                result.add(Pair("*",table["*"]!!))
                i++
            }
            '/' ->{
                result.add(Pair("/",table["/"]!!))
                i++
            }
            '+' ->{
                result.add(Pair("+",table["+"]!!))
                i++
            }
            '-' ->{
                result.add(Pair("-",table["-"]!!))
                i++
            }
            else -> {
                //如果是数字
                if (Character.isDigit(source[i])) {
                    var number = ""
                    while (i < source.length && Character.isDigit(source[i])) {
                        number += source[i++]
                    }
                    result.add(Pair(number, 11))
                } else if (source[i].isAlphaBetic()) {
                    var ide = ""
                    while (i < source.length && source[i].isAlphaBetic()) {
                        ide += source[i++]
                    }
                    result.add(Pair(ide, 10))
                } else {
                    throw IllegalArgumentException("没有这样的运算符${source[i]}")
                }
            }
        }
    }
}

private fun parseDetail2(source: String){
    val number   = "[0=9]"
    val variable = "[a-z][a-z|0-9]*"
    //正则表达式匹配是贪心和懒惰的
    //匹配出来一个表达式后要检验 比如 会出现 /- 的情况...
    val op = "[-+*/][-+*/]*=*"
    //匹配的case 可能是
    // xy1234 = 1
    // xysomething += something
    //没有做类型检查
    val expression = "([a-z][a-z|0-9]*)([-+*/:=][-+*/]*=*)([a-z|0-9]+)"
    val pattern = Pattern.compile(expression)
    val mattcher = pattern.matcher(source.replace(" ",""))

    lateinit var leftNode :String
    lateinit var ops :String
    lateinit var rightNode :String

    if(mattcher.find()){
        leftNode = mattcher.group(1)
        ops = mattcher.group(2)
        rightNode = mattcher.group(3)
    }

    //检查提取的运算符类型
    if(!table.keys.contains(ops))
        result.add(Pair(ops,41))

    result.add(Pair(ops,table[ops]!!))

    //如果不是变量类型将会也保存 但是是语法错误的
    //左值一定是变量类型
    result.add(Pair(leftNode,10))

    //可能没有右边的值
    if(rightNode == null)
        return;
    if(isIdentifier(rightNode))
        result.add(Pair(rightNode,10))
    else
        result.add(Pair(rightNode,11))
}


fun Char.isAlphaBetic():Boolean = this in 'a' .. 'z' || this in 'A' .. 'Z'

fun main(args:Array<String>){

//   val f =  isNumber("0")
    parse(sourceString = testCase3)

    result.forEach {
        println(it)
    }
}