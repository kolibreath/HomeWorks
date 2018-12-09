import java.io.*
import java.lang.IllegalArgumentException
import java.util.regex.Pattern

const val testCase1=  "begin x :=9; if x > 0 then x := 2*x + 1/3; end #"
const val testCase2=  "begin x := 10; y=x; end #"
const val testCase3 = "begin if x==1 then x := 2"
var result = ArrayList<Pair<String,Int>>()

fun parse(sourceString :String): ArrayList<Pair<String,Int>>{
    initTable()

    val result = ArrayList<Pair<String,Int>>()
    //先将这个表达式大体分开

    val regex = Regex("\\s+")
    val temp = regex.replace(sourceString,"&").split("&").filter {
        it != "" || it != " " }
    val array = temp.filter { it !="" }
    val keySet = table.keys

    for(i in array){
        when {
        //是一个关键字
            i in keySet -> result.add(Pair(first = i,second = table[i]!!))
        //可能是一个标示符
            isIdentifier(i) -> result.add(Pair(first = i,second = 10))
            isNumber(i) -> result.add(Pair(first = i,second = 11))
            else ->
                //只可能是一个表达式
                //或者是一个block
                // {
                // }
                parseDetail(i,result)
        }
    }

    return result
}

fun isNumber(source:String):Boolean{
    if(source[0] in 'a' .. 'z' || source[0] in 'A' .. 'Z')
        return false

    var parse = source
    if(source.startsWith("0x") || source.startsWith("0X"))
        parse  = parse.substring(2,source.length)

    parse.forEach {
        val i = it.toInt() - 48
        if(i !in 0 .. 9){
            return false
        }
    }
    return true
}
fun isIdentifier(source:String):Boolean{
    val pattern = Pattern.compile("[_]*[a-z|A-Z|\\u4e00-\\u9fa5][0-9|a-z|A-Z]*")
    return pattern.matcher(source).matches()
}

private fun parseDetail(source: String,result:ArrayList<Pair<String,Int>>) {
    var i = 0
    while (i < source.length) {
        when (source[i]) {
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
            '(' ->{
                result.add(Pair("(",table["("]!!))
                i++
            }
            ')' ->{
                result.add(Pair(")",table[")"]!!))
                i++
            }

            '{' ->{
                result.add(Pair("{",table["{"]!!))
                i++
            }
            '}' ->{
                result.add(Pair("}",table["}"]!!))
                i++
            }
            else -> {
                //如果是数字
                when {
                    Character.isDigit(source[i]) -> {
                        var number = ""
                        while (i < source.length && Character.isDigit(source[i])) {
                            number += source[i++]
                        }
                        result.add(Pair(number, 11))
                    }
                    source[i].isChar() -> {
                        var ide = ""
                        while (i < source.length && source[i].isChar()) {
                            ide += source[i++]
                        }
                        result.add(Pair(ide, 10))
                    }
                    else -> throw IllegalArgumentException("没有这样的运算符${source[i]}")
                }
            }
        }
    }
}

fun Char.isChar():Boolean = this in 'a' .. 'z' || this in 'A' .. 'Z'

fun file2Code(src:String):String{
   val file  =File(src)
    return file.readText()
}

