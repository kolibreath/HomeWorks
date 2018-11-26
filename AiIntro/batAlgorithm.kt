import java.lang.Exception
import java.util.*

/***
 * the implementation of Bat Algorithm in Kotlin
 */

//the number of variables in function f : f(x1,x2,x3,.....,xn)
const val dimension = 3
//the population of bats
const val population = 500
//the iteration times of bats
const val generation = 500
const val lowerBound = -4.0
const val uppperBound = +4.0

var variable = 0.01
var pulseRate = 3
var loudness = 7.toDouble()
var decay = 0.95

val random = Random()
/**
 * dont set the initial value to big!
 * better initialize with the upper bound and lower bound
 *
 * narrow down initial locations
 */
val randomLocation= {
    val random = lowerBound + (uppperBound- lowerBound) * random.nextDouble()
    random
}


val batPopulationLocation = Array(population){
    DoubleArray(dimension){
        randomLocation.invoke()
    }
}

//2d array ith bat and the velocity in different direction
val batPopulationVelocity = Array(population) {
    DoubleArray(dimension) {
        random.nextDouble()
    }
}

val batPopulationFrequency = DoubleArray(population){
    random.nextDouble()
}

val batPopulationPulseRate = DoubleArray(population){
    0.5
}

var bestLocation  = batPopulationLocation[0]
var bestValue  = objective(batPopulationLocation[0])



//the objective function
fun objective(xi:DoubleArray):Double {
    var sum :Double = 0.toDouble()
    for(x in xi)
        sum += x*x

    return sum
}

fun startBats(generation :Int) {
    var copy = generation
    while (copy-- >= 0) {

        //iterate every bat!
        for (location in batPopulationLocation.withIndex()) {

            //the ith bat
            val i = location.index
            batPopulationFrequency[i] = random.nextDouble()

            //update velocity the bat will fly to a random location
            for (temp in location.value.withIndex()) {
                //todo buggy overflow error ? the array sometimes  length is 4
                if (location.index > 3)
                    break

                val j = temp.index
                val x = temp.value

                try {
                    batPopulationVelocity[i][j] =
                            (x - bestLocation[j]) * batPopulationFrequency[i] + batPopulationVelocity[i][j]
                } catch (e: Exception) {
                }
            }


            //the velocity * timeSlice is a distance and the timeSlice is 1
            //store the location temporarily
            for (velocity in batPopulationVelocity[i].withIndex()) {
                val j = velocity.index
                batPopulationLocation[i][j] = batPopulationVelocity[i][j] * 1
                +batPopulationLocation[i][j]
            }

            //using rate to convergence
            if (random.nextDouble() * 10 > pulseRate)
                for (location in batPopulationLocation[i].withIndex()) {
                    batPopulationLocation[location.index] = bestLocation + variable * random.nextGaussian()
                    variable *= decay
                }

            val objectValue = objective(batPopulationLocation[i])
            if (objectValue <= bestValue && random.nextDouble() * 10 < loudness) {
                   println("objective value $objectValue")
                loudness *= decay
                pulseRate /= pulseRate
                bestValue = objectValue
                bestLocation =  batPopulationLocation[i]
            }


            }
        }
    }

    fun main(args: Array<String>) {
        batPopulationLocation[batPopulationLocation.size - 1] = doubleArrayOf(0.0, 0.0, 0.0)
        startBats(generation)


    bestLocation.forEach {
        print("locate at ")
        print( " $it")
    }
    }





