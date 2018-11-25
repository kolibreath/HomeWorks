/***
 * the implementation of Bat Algorithm in Kotlin
 */

//todo buggy : the potential overflow by double * double !
//the number of variables in function f : f(x1,x2,x3,.....,xn)
const val dimension = 3
//the population of bats
const val population = 30
//the iteration times of bats
const val generation = 1000

//todo buggy this maybe modified for better value!
var pulseRate = 7
var loudness = 7.toDouble()
var decay = 0.95



val randomLocation= {
    // negative or positive
    val np = intArrayOf(+1, -1)
    //the max value is 10e5
    val power = Math.random()*10%5
    val random :Double = np[(Math.random()*10).toInt()%2].times(Math.pow(10.toDouble(), power))
    random
}

val randomVelocity = {Math.random()*10}

val batPopulationLocation = Array(population){
    DoubleArray(dimension){
        randomLocation.invoke()
    }
}


val batPopulationVelocity = DoubleArray(population){
    randomVelocity()
}

val batPopulationFrequency = DoubleArray(population){
    Math.random()
}

val batPopulationPulseRate = DoubleArray(population){
    0.5
}

var bestLocation  = DoubleArray(dimension)
var bestValue  = objective(batPopulationLocation[0])



//the objective function
fun objective(xi:DoubleArray):Double {
    var sum :Double = 0.toDouble()
    for(x in xi)
        sum += x*x

    return sum
}

fun startBats(generation :Int){
    var copy = generation
    while(copy-- >0 ){
        //iterate every bat!

        for(array in batPopulationLocation.withIndex()) {

            //the ith bat
            val i = array.index
            batPopulationFrequency[i] = Math.random()

            //update velocity the bat will fly to a random location
            for (temp in array.value.withIndex()) {
                val index = temp.index
                val value = temp.value

                batPopulationVelocity[index] =
                        (value - bestLocation[index]) * batPopulationFrequency[index] + value
            }

            //the movements of bats
            val tempLocation = DoubleArray(dimension)

            //the velocity * timeSlice is a distance and the timeSlice is 1
            //store the location temporarily
            for (velocity in batPopulationVelocity.withIndex()) {
                tempLocation[velocity.index] = batPopulationVelocity[velocity.index] * 1
                +batPopulationLocation[i][velocity.index]
            }

            //using rate to convergence
            if (Math.random() * 10 > pulseRate)
                for (location in batPopulationLocation.withIndex()) {
                    batPopulationLocation[location.index] = bestLocation + 0.001 * Math.random()
                }

            val objectValue = objective(batPopulationLocation[i])
            if (objectValue < bestValue && Math.random() * 10 < loudness) {
                loudness *= decay
                pulseRate /= pulseRate

                batPopulationLocation[i] = tempLocation
                bestValue = objectValue
                bestLocation = tempLocation
            }

        }
    }
}

fun main(args:Array<String>){
   startBats(generation)

    println("best value +$bestLocation")
}




