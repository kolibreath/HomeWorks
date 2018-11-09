/**
 * 使用Kotin 协程
 * Kotlin 实现观察者模式 上游发射一些 进程（模拟进程的进入）
 * 下游的观察者处理这些进程
 *
 * 如果检测到queue 进程队列中
 *
 * 是不是可以使用Channel 模拟 进程队列？
 */
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.properties.Delegates


const val waiting = 0
const val running  = 1
const val finished = 2

data class PCB(
        val name:String,
        val arriveTime:String,
        var priority :Int,
        var status :Int,
        val needTime:Int,
        var usedTime:Int)

val processQueue = LinkedList<PCB>()
val finishedQueue = LinkedList<PCB>()


var flag = false
var size: Int by Delegates.observable(
        initialValue = processQueue.size,
        onChange = { _, _, newValue ->
            if (newValue >= 1)
                flag = true
        }
)

fun handle(){
    GlobalScope.launch{
        println("running")
            while (!flag) {
                println("handle process is waiting...")
                delay(100L)
        }

        delay(1000L)
        while(processQueue.isEmpty().not()){
            processQueue.prioritySort()
            //模拟时间片的分配
            val first = processQueue.poll().apply {
                println("current running process $this")
                this.status = running
                this.usedTime++
            }
            if(first.usedTime == first.needTime){
                finishedQueue.add(first)
                println("a job is completed!")
                println("finished queue ${finishedQueue.size}")
            }else{
                first.status = waiting
                first.priority --
                processQueue.add(first)
                size = processQueue.size
            }
            delay(1000L)
        }
    }
}

fun LinkedList<PCB>.prioritySort(){
    this.sortByDescending {
        it.priority
    }
}

fun incomingProcess(number:Int){

    //进程进来的时刻是不确定的
    GlobalScope.launch {
        var counter = 0
             repeat(number){
             val random = Math.random()* 10
            delay((random*100L).toLong())
            val pcb = PCB(
                    name = "process ${counter++}",
                    arriveTime = System.currentTimeMillis().toString(),
                    priority = random.toInt() ,
                    status = waiting,
                    //避免随机产生的 需要运行的时间是0
                    needTime = random.toInt() + 1,
                    usedTime =  0
            )
            println("incoming process $pcb")
            processQueue.add(pcb)
                 size = processQueue.size
        }
    }
}


fun main() {
//    incomingProcess(10)
//    handle()
    GlobalScope.launch {
        val something = async {
            delay(2000L)
            5
        }.await()

        val somethingElse = async {
            delay(1000L)
            10
        }.await()

        println( something + somethingElse)
    }
    while(true){

    }
}