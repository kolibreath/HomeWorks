import java.util.*

class TestAlgorithm(private val i:Int) :BatAlgorithm() {


    override fun objective(xi: DoubleArray): Double {
        val function = Functions()
        return function.runAll(xi,dimension,i)
    }
}

fun main(args:Array<String>) {

    lateinit var algrithm :TestAlgorithm
    val list = listOf("sphere", "schwefel", "schwefel2",
            "schwefel3", "rosenBrock", "step", "quarticWithNoise", "rastrign", "ackley"
            , "griewank", "penalized", "penalized2", "schwefel4")

    list.forEachIndexed { index, s ->
        algrithm = TestAlgorithm(index)
        algrithm.initBats()

        //收藏进行30次这个运算的最好值！
        val container = LinkedList<Double>()
        repeat(30) {
            algrithm.startBats(generation)
            container.add(algrithm.bestValue)
        }
        println("the best value of $s is "+container.min())
    }
}