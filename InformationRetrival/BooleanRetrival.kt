import java.io.File
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap
import kotlin.collections.HashSet

val allDocs = arrayListOf("doc5.txt","doc4.txt","doc3.txt","doc2.txt","doc1.txt")
val indexNameMap = mapOf(Pair(1,"doc1.txt"),Pair(2,"doc2.txt"),Pair(3,"doc3.txt"),Pair(4,"doc4.txt"),Pair(5,"doc5.txt"))
val docs = {
    val array = Array(size = 5,init = {""})
    var index = 0;
    while(index < 5){
        array[index] = "/home/kolibreath/githubProject/InformationSearch/src/doc${index+1}.txt"
        index ++
    }
    array
}
fun createMatrix(src:Array<String>){

}

fun createInvertedIndex(src:Array<String>):HashMap<String,InvertedIndex>{


    //the name of the token is the key
    //the value is the invertedIndex
    val tokens = HashMap<String,InvertedIndex>()

    src.forEachIndexed { index, s ->

        val text = file2Code(s)
        val array = text.split(" ")

        array.forEach {
            val key = it.toLowerCase()
            if(tokens.keys.contains(key).not()){
                val d = LinkedList<String>().apply {
                    add(indexNameMap[index+1]!!)
                }
                tokens[key] = InvertedIndex(name = key, docIds = d)
            }else{
               //if contains the key : analysis
                val value = tokens[key]!!.docIds
                value.add(indexNameMap[index+1]!!)
            }
        }
    }

    tokens.forEach {
        val ids = it.value.docIds
        ids.sort()
    }

    return tokens
}

fun file2Code(src:String):String{
    val file  =File(src)
    return file.readText()
}

fun booleanRetrival(command:String,docmentInvertedIndex: HashMap<String,InvertedIndex>):MutableCollection<String>{
    //给出一个布尔查询并且返回查询结果！
    //输出：符合条件的文档编号和句子

    fun isWord(word:String):Boolean = !(word == "and" || word == "not" || word == "or")

    fun parseCommand():LinkedList<String>{

        val reversedPoland = LinkedList<String>()
        val opStack = LinkedList<String>()
        var notFlag=  false
        var first = false
        command.split(" ")
                .forEach {

                    //如果是一般的字符串 token 直接加入
                   if(isWord(it)){
                       reversedPoland.add(it)
                   }else if(it == "not"){
                       notFlag =true
                   }else {
                       //如果是操作符
                       //and 的优先级高于 or
                       //and 和 not 同一个级别
                       //如果是and 和 not 的话可以直接加入
                       if(it == "and" ){
                           opStack.push(it)
                       }else {
                           if(opStack.size == 0 || opStack.peek() == "or") {
                               opStack.push(it)
                               return@forEach
                           }
                           if(opStack.peek() == "and" || opStack.peek() == "not") {
                               while(opStack.peek() != "or"){
                                   reversedPoland.add(opStack.pop())
                               }
                               opStack.push(it)
                           }
                       }
                   }
                    if(notFlag && first){
                        notFlag = false
                        reversedPoland.add("not")
                    }
                    first = true
                }

        while(!opStack.isEmpty()){
            reversedPoland.add(opStack.pop())
        }

        return reversedPoland
    }

    //求不包含这个key的集合
    //求这个集合的补集
    fun excludeSection(key:String) :LinkedList<String> {
        val excludeSection = LinkedList<String>()
        val contains = docmentInvertedIndex[key]!!.docIds
        allDocs.forEach {
            if(!contains.contains(it)){
                excludeSection.add(it)
            }
        }
        return excludeSection
    }

    //求两个集合的交集
    fun interSection(a:String,b:String):LinkedList<String>{
        val aValue = try{
            docmentInvertedIndex[a]?.docIds?: LinkedList()
        }catch (e:Exception){
            LinkedList<String>()
        }
        val bValue = try{
            docmentInvertedIndex[a]?.docIds?: LinkedList()
        }catch (e:Exception){
            LinkedList<String>()
        }

        val result = LinkedList<String>()

        aValue.forEach {
            if(bValue.contains(it) && !result.contains(it))
                result.add(it)
        }

        bValue.forEach {
            if(aValue.contains(it) && !result.contains(it)){
                result.add(it)
            }
        }

        return result

    }


    val resultSet= HashSet<String>()

    val reversedPoland = parseCommand()
    //计算和维护当前的情况
    val tempStack = LinkedList<String>()

    for(token in reversedPoland){
        if(token == "not") {
            resultSet.addAll(excludeSection(tempStack.pop()))
        }
        if(token == "and"){
            val right = try {
                tempStack.pop()!!
            }catch (e:Exception){
                ""
            }
            val left = try {
                tempStack.pop()!!
            }catch (e:Exception){
                ""
            }
            val intersection = interSection(right,left)
            resultSet.addAll(intersection)
        }
        if(token == "or"){
            val right = try {
                docmentInvertedIndex[tempStack.pop()]!!.docIds
            }catch (e:Exception){
                LinkedList<String>()
            }
            val left =try {
                docmentInvertedIndex[tempStack.pop()]!!.docIds
            }catch (e:Exception){
                LinkedList<String>()
            }
            resultSet.addAll(right)
            resultSet.addAll(left)
        }
        if(isWord(token)){
            tempStack.push(token)
        }
    }

    if(!tempStack.isEmpty()){
        resultSet.addAll(docmentInvertedIndex[tempStack.pop()]!!.docIds)
    }

    return resultSet.toMutableList()
}

fun main(args:Array<String>){
    val docments = docs()

    val list = createInvertedIndex(docments)
    val result = booleanRetrival("not semantic or english",list)
    if(result.isEmpty())
        println("no result found")
    else{
        result.forEach {
            val line = file2Code("/home/kolibreath/githubProject/InformationSearch/src/$it")
            println(line)
        }
    }
}