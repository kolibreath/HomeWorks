import org.w3c.dom.stylesheets.DocumentStyle
import java.io.File
import java.util.*
import kotlin.math.log2

lateinit var query:String
const val N:Int = 1000

data class Word(val name:String,
                var tf:Int = 0,
                //文档数目是一个随机数
                var df: Int,
                var idf:Double = 0.0,
                //使用a公式计算 tf 因子
                var atf:Double = 0.0,
                //使用t公式计算 df 因子
                var tdf:Double = 0.0,
                //使用c公式计算 w因子
                var cwf:Double = 0.0)

/**
//这个查询的单词在这个文档中出现的次数
var tf :Int = 0,
//这个单词在所有的文档中出现的次数
var df:Double ,
var idf:Double = 0.0,
//使用n公式计算 tf 因子
var atf:Double = 0.0,
//使用t公式计算 df 因子
var tdf:Double = 0.0,
//使用c公式计算 w因子
var cwf:Double = 0.0,
 */
data class Document(val name:String,
                    val words:MutableList<Word>,
                    var score :Double= 0.0)


val words = mutableListOf<Word>()
val documents = mutableListOf<Document>()

val inversesDocumentFrequency = { df:Double -> log2((N/df)) }

//a公式计算查询的tf因子
val auamentedForTf = { tf:Int  , maxTf:Int -> 0.5 + (0.5 * tf)/ maxTf }
//t公式计算查询/文档的df因子
val tidfFordf      = { df:Double -> log2(N/df) }
//归一化操作查询/文档当前的tf 占整个归一化操作的比例
val cosineForCf = {tf:Int ->
    var sum = 0
    words.forEach {
        sum += it.tf* it.tf
    }
    tf/Math.sqrt(sum.toDouble())
}

//文档tf因子的n公式
val naturalFortf = {tf:Int -> tf}



//得到一个已经计算过了重复次数的向量
fun createWordVector(){
    val queries = query.split(" ")

    //统计查询中的tf
    var step  = 0
    queries.sorted()
            .forEachIndexed { index, s ->
                if(step  !=0 ) {
                    step --
                    return@forEachIndexed
                }
                var counter = 0
                while( step + index < queries.size && queries[step + index] == s){
                    counter ++
                    step++
                }
                val randomDf = Math.random()*10* 1000L
                val word = Word(name = s, tf = counter, df = randomDf.toInt() , idf =inversesDocumentFrequency(randomDf) )
                words.add(word)
    }

    val maxtf = words.maxBy { it.tf }!!.tf
    words.forEach {
        it.atf = auamentedForTf(it.tf,maxtf)
        it.tdf = tidfFordf(it.df.toDouble())
        it.cwf = cosineForCf(it.tf)
    }
}

//计算文档中的文档频率
val calculateDfInDocuments = {
    word:String ->
    var counter =  0
    arrayListOf("doc5.txt","doc4.txt","doc3.txt","doc2.txt","doc1.txt")
            .forEach label@ {
                val src = "/home/kolibreath/githubProject/InformationSearch/src/$it"
                val text = File(src).readText()
                text.split(" ").forEach  {
                    if(it == word){
                        counter++
                        return@label
                    }
                }
            }
    counter
}

val calculateTfInDocument = { src:String ->
        val text = File(src).readText()
        //计算一个单词在文档中的此项频率
        var counter = 0
        words.forEach {word ->
            //计算tf
            text.split(" ")
                    .forEach { token ->
                        if(token == word.name){
                            counter++
                        }
                    }

        }
    counter
}

fun createDocumentVector(){
    arrayListOf("doc5.txt","doc4.txt","doc3.txt","doc2.txt","doc1.txt")
            .forEach { src ->

                val wordlist = mutableListOf<Word>()
                words.forEach { word ->

                    val tf = calculateTfInDocument("/home/kolibreath/githubProject/InformationSearch/src/$src")
                    val df = calculateDfInDocuments(word.name).toDouble()
                    val idf = inversesDocumentFrequency(df)

                    val word = Word(
                            name = word.name,
                            tf = tf,
                            df = df.toInt(),
                            idf = idf,
                            //ntf
                            atf =  naturalFortf(tf).toDouble(),
                            tdf = tidfFordf(df),
                            cwf = cosineForCf(tf)
                    )
                    wordlist.add(word)
                }

                //计算文档的得分 两个words 和 wordlist
                var sum = 0.0
                for(i in words.indices){
                    sum += words[i].cwf * wordlist[i].cwf
                }

                documents.add(Document(
                        name = src,
                        words =  words,
                        score =  sum
                ))
            }
}

fun main(args:Array<String>){
    query = Scanner(System.`in`).nextLine()
    createWordVector()
    createDocumentVector()

    documents.sortByDescending {
        it.score
    }
    documents.take(3)
            .forEach {
                println(it.name)
            }

}