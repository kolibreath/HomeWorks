import java.util.concurrent.locks.Condition


//简单的语法分析程序
//分析完成之后的程序会自动将index ++!
//todo what is mutableList
//list 的结构是MutableList!

fun statementNode(result: MutableList<Pair<String, Int>>, index: Int = 0):Int {
    var curIndex = index
    return curIndex
}

//检查是否是一个表达式 或者 标示符
fun expressionNode(expression:MutableList<Pair<String,Int>>, index:Int = 0):Int{
    return 0
}

fun conditionNode(result: MutableList<Pair<String,Int>>, start:Int , end:Int):Int{
    var curIndex = start
    //开始
    //找到 == 的index
    var counter = 0
    result.drop(curIndex)
            .forEach {
                val id = it.second
                if(id ==39)
                    return@forEach
                else
                    counter++
            }
    val equalIndex = curIndex + counter

    //校验左边是不是一个 表达式 或者标示符
    val leftSubList = result.subList(start, equalIndex)
    expressionNode(leftSubList)
    //校验右边是不是一个 表达式 或者标示符
    val rightSubList = result.subList(equalIndex + 1 , end )
    expressionNode(rightSubList)
    return end + 1
}
//如果读取的时候读到了while 循环 此时调用这个函数
//eg: while(result, index-1)
//index 在调用的时候需要-1
//reason : 看起来比较舒服! debug 也比较方便
//返回值是处理完成这个while node 之后的index
fun whileNode(result:MutableList<Pair<String,Int>>,index:Int):Int{
    var curIndex = index
    val whileKeyWord = result[curIndex++]
    val leftParenthesis = result[curIndex++].second

    //从第index开始
    // a b c while ( i == 1)
    //即从 ( 开始
    var counter= 0
    result.drop(index)
            .forEach {
                val id = it.second
                if(id == 27)
                    return@forEach
                else
                    counter++
            }

    val rightParenthesisIndex = curIndex + counter

    val rightParenthesis = result[rightParenthesisIndex].second

    //左边和右边不相等 ： 抛出异常！
    if(leftParenthesis != 26 || rightParenthesis != 27 )
        throw AnalyzerException("(while loop) parenthesis will not match!")


    //subList inclusive curIndex, exclusive curIndex + counter + 1
    curIndex = conditionNode(result.subList(curIndex, rightParenthesisIndex + 1)
    ,start = curIndex , end = curIndex + counter )

    //todo 调用statement 处理中间的


    //可以没有{}
    //但是有左边一定得有右边！
    val leftColumn = result[curIndex++].second

    val rightColum = result[statementNode(result,curIndex)].second

    if(rightColum != 31 || leftColumn != 30)
        throw AnalyzerException("(while loop) columns will not match!")

    return curIndex
}


fun ifNode(result: MutableList<Pair<String, Int>>, index:Int){
    var curIndex = index
    val ifNode = result[curIndex++]
    val leftParenthesis = result[curIndex]
}



class AnalyzerException(msg:String):Throwable()