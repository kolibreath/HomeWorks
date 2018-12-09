import java.lang.Exception

//简单的词法分析程序

lateinit var parseResult :ArrayList<Pair<String,Int>>

var index = 0

//程序块
fun program():Boolean{
    val begin = parseResult[index++].first
    if(begin != "begin")
        throw AnalyzerException("$index 需要使用begin 语句开头")

    val main = parseResult[index++].first
    if(main!="main")
        throw AnalyzerException("$index 需要使用main语句作为函数体")

    return blockNode()
}

//语句串分析
fun sentenceString():Boolean{
    //重复一次到多次！
    val firstSentence = sentence()
    //检查后面的是不是分号
    val back = parseResult[index++].first
    return if(firstSentence && back == ";") {
        sentenceString()
    }else{
       throw AnalyzerException("表达式没有以分号结尾")
    }

}


//语句分析
fun sentence():Boolean{
    //有可能是一个没有while if语句的串
    //因为是或者关系 需要加一个mute 不让报错停止程序
    //通过try-catch 方式！

    //扫描一下现在的start是什么
    val start = parseResult[index]
    return when(start.first){
        "while"->{
            index++
            val item = parseResult[index]
            if(item.first == "end")
                throw AnalyzerFinisheException("扫描完成,正常结束")
            whileNode()
        }
        "if"->{
            index++
            val item = parseResult[index]
            if(item.first == "end")
                throw AnalyzerFinisheException("扫描完成,正常结束")
            ifNode()
        }
        //其他情况就是assign
        else->{
            //todo buggy
            index++
            //检查完这个符号之后要++
            val item = parseResult[index++]
            if(item.first == "end")
                throw AnalyzerFinisheException("扫描完成,正常结束")
            assignNode()
        }
    }
}

//语句块分析
fun blockNode():Boolean{
    val left = parseResult[index++].first
    val sentenceString = sentenceString()
    if(!sentenceString)
        return false

    val right = parseResult[index++].first

    if(left != "{" || right != "}")
        throw AnalyzerException("$index 缺少结束符号{ 或者 }")

    return true
}

//while node
fun whileNode():Boolean{

    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")

    val condition = conditionNode()
    val blockNode = blockNode()
    return true
}

//todo
//赋值语句
fun assignNode():Boolean{

    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")

    val rightValue = expressionNode()
    //最后应该以分号结尾
    return rightValue
}
//if 语句分析
fun ifNode():Boolean{

    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")


    val condition = conditionNode()
    val block = blockNode()

    return false
}


//condition node语句分析
//并且会分析condition 左右两侧的括号
fun conditionNode():Boolean{

    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")

    val leftColumn = parseResult[index++].first
    if(leftColumn !="(")
        throw AnalyzerException("$index 缺少左括号 (")

    val leftValue = expressionNode()


    val comparator = when(parseResult[index].first){
        ">=" -> true
        "<=" -> true
        "==" -> true
        else -> false
    }
    if(!comparator)
        throw AnalyzerException("$index 缺少比较符号")

    index++

    expressionNode()

    val rightColum = parseResult[index].first

    index ++
    if(rightColum!=")")
        throw AnalyzerException("$index 缺少右括号")

    return true
}

//表达式分析
/***
 * return true表示扫描完成一个或者多个项
 */
fun expressionNode():Boolean{
    //如果第一符号不是项
    //这个时候将所有的<因子>部分消耗完成 return true表示<？> +|- <?>中？表示的是一个项
    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")
    val first = itemNode()

    if(first){

        val temp = index
        //不一定需要这样的消耗
        val append = { pair: Pair<String, Int> ->
            if (pair.first == "+" || pair.first == "-") {
                index++
            }
        }

        val item = parseResult[index]
        //消耗
        append(item)
        if(index > temp){
            itemNode()
        }
    }
    return first
}


//项分析
/***
 * 返回true表示满足一个或者多个因子 （多个因子相乘或者相除）
 */
fun itemNode():Boolean{
    //项的组成：<因子>{* <因子> | / 因子}
    //如果index指向的部分是一个因子

    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")

    val first = factorNode()

    if(first) {
        //如果是真的 说明可能还要继续分析
        //如果可以继续分析

        val temp = index
        //超前扫描 扫面后面一个运算符号
        //附加结构消耗完成*<因子> | /<因子>的部分
        val append = { pair: Pair<String, Int>->
            if (pair.first == "*" || pair.first == "/") {
                index++
            }
        }
        val item = parseResult[index]

        //扫描后面一个运算符
        append(item)
        if(index > temp){
            //后面可能是一个项
            factorNode()
        }
        //else 在else的情况下index 不会增加
    }

    return first
}

//最底层
//因子分析
/***
 * return true表示这个是一个数字或者是标示符
 * 或者是一个表达式 在表达式中进行判断
 */
fun factorNode( ):Boolean{

    if(parseResult[index].first == "end") throw AnalyzerFinisheException("分析完成 正常结束")


    val id = parseResult[index].second
    return if(id == 11 || id == 10 ) {
        index  ++
        true
    }else if ( parseResult[index].first == "==" || parseResult[index].first == ">=" || parseResult[index].first
    == "<="){
        throw AnalyzerException("表达式语法错误")
    } else{
        expressionNode()
    }


}

class AnalyzerException(msg:String):Exception(msg)

class AnalyzerFinisheException(msg:String):Exception(msg)

fun main(args:Array<String>) {

    val source = file2Code("/home/kolibreath/githubProject/complier/src/TestCase.txt")
    parseResult = parse(sourceString = source)


    try {
        println(program())
    } catch (e: AnalyzerFinisheException) {
        println(e.message)
    } catch (e: AnalyzerException) {
        println(e.message)
    }

}

