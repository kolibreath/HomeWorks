import java.util.*
import kotlin.math.sqrt

/***
 * the implementation of Bat Algorithm in Kotlin
 */


//the number of variables in function f : f(x1,x2,x3,.....,xn)
const val dimension = 3
//the population of bats
const val population = 500
//the iteration times of bats
const val generation = 10000
const val lowerBound = -5.0
const val upperBound = +5.0

abstract class BatAlgorithm {
    var variable = 1.0
    var pulseRate = 3
    var loudness = 10.toDouble()
    var decay = 0.95

    val random = Random()
    /**
     * dont set the initial value to big!
     * better initialize with the upper bound and lower bound
     *
     * narrow down initial locations
     */
    val randomLocation = {
        val random = lowerBound + (upperBound - lowerBound) * random.nextDouble()
        random
    }


    val batPopulationLocation = Array(population) { DoubleArray(dimension) }

    //2d array ith bat and the velocity in different direction
    val batPopulationVelocity = Array(population) {
        DoubleArray(dimension) {
            random.nextDouble()
        }
    }

    var bestLocation = batPopulationLocation[0]
    var bestValue = 100000.0


    fun initBats() {

        //双重for循环
        var i = 0
        var j = 0
        while (i < population) {
            while (j < dimension) {
                batPopulationLocation[i][j++] = randomLocation()
            }
            i++
        }
    }

    //the objective function
//    fun objective(xi: DoubleArray): Double {
//        val function = Functions()
//        return function.schwefel(xi, dimension)
//    }

    abstract fun objective(xi:DoubleArray):Double

    fun startBats(generation: Int) {
        var copy = generation
        while (copy-- >= 0) {

            //iterate every bat!
            for (bat in batPopulationLocation.withIndex()) {

                //the ith bat
                val i = bat.index
                val frequency = random.nextDouble() * ((2 + 2)) - 2
//            println(batPopulationFrequency[i])
                //update velocity the bat will fly to a random location
                for (temp in bat.value.withIndex()) {

                    if (bat.value.size > 3) break
                    val j = temp.index
                    val x = temp.value
                    batPopulationVelocity[i][j] =
                            (x - bestLocation[j]) * frequency + batPopulationVelocity[i][j]
                }


                //the velocity * timeSlice is a distance and the timeSlice is 1
                //store the location temporarily
                for (velocity in batPopulationVelocity[i].withIndex()) {
                    val j = velocity.index
                    batPopulationLocation[i][j] = batPopulationVelocity[i][j] * 1
                    +batPopulationLocation[i][j]
                }

                //进行修正坐标
                simpleBounds(batPopulationLocation[i])

                //using rate to convergence
                if (random.nextDouble() * 10 > pulseRate)
                    for (location in batPopulationLocation[i].withIndex()) {
                        batPopulationLocation[location.index] = bestLocation + variable * loudness
                        variable *= decay
                    }

                val objectValue = objective(batPopulationLocation[i])
                if (distance(batPopulationLocation[i],bestLocation)
                        <= bestValue && random.nextDouble() * 10 < loudness) {
                    println("objective value $bestValue")
                    loudness *= decay
                    pulseRate /= pulseRate
                    bestValue = objectValue
                    bestLocation = batPopulationLocation[i]
                }


            }
        }
    }

    private fun distance(curLocation:DoubleArray, bestLocation:DoubleArray):Double{
        var sum = 0.0
        var counter = 0;
        while(counter < dimension){
            val a =  curLocation[counter] - bestLocation[counter]
            sum += a * a
            counter ++
        }
        return sqrt(sum)
    }

    private fun simpleBounds(location:DoubleArray){
        for(i in location.withIndex()){
            if(i.value !in lowerBound..upperBound){
                location[i.index] = (upperBound - lowerBound)*random.nextDouble() + lowerBound
            }
        }
    }
}



