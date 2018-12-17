import java.util.*

class JCB(val startTime:Int,
          val endTime:Int,
          val occupy:Int){
    //占用内存的开始
    var start:Int = 0
    //占用内存的结束
    var end :Int = 0
    var occupied:Boolean = false
}


val random = Random()

//初始化待运算的作业队列
val jobQueue = LinkedList<JCB>()
//处理中的作业队列
val workingQueue = LinkedList<JCB>()

val memoryQueue = LinkedList<JCB>()

fun initMemoryQueue(number: Int){
    //初始化第一次停止时间
    var lastStartTime = 0
    var lastEndTime:Int = (random.nextDouble()*100).toInt()
    //占用的内存空间
    var occupy :Int= (random.nextDouble()*100).toInt()
    repeat(number){
        val job = JCB(lastStartTime, lastEndTime,occupy)
        lastStartTime = lastEndTime +  (random.nextDouble()*100).toInt()
        lastEndTime  = lastStartTime + (random.nextDouble()*100).toInt()
        occupy = (random.nextDouble()*100).toInt()
        jobQueue.add(job)
    }
}

//找出空闲的list
fun findSegmentation():LinkedList<Pair<Int,Int>>{

    val list = LinkedList<Pair<Int,Int>>()
   var counter= 0
    while(counter < memoryQueue.size){
        val thisOne = memoryQueue[counter]
        if(thisOne.occupied){
            if(counter + 1 < memoryQueue.size){
                val nextOne = memoryQueue[counter + 1]
                val thisEnd = thisOne.end
                val nextStart = nextOne.start
                //确定出一个没有被使用的部分
                val pair = Pair(thisEnd,nextStart)
                list.add(pair)
            }
            counter ++
        }
    }

    return list
}



//process working queue
//将已经完成的任务清除出去
fun processWorkingQueue(cur:Int){
  for(job in workingQueue){
      if(cur > job.endTime){
          workingQueue.remove(job)
          for(memory in memoryQueue){
              if(memory.start == job.start && memory.end == job.end){
                  memoryQueue.remove(memory)
              }
          }
      }
  }
}

//开始作业分配内存
//参数 表示使用地算法的下标
fun findPosition(index:Int,waitingJob:JCB):Pair<Int,Int>{
    lateinit var startEnd : Pair<Int,Int>
    when(index){
        //使用先进先出算法
        0 ->{
            val segementations = findSegmentation()
            for(seg in segementations){
                val length = seg.second - seg.first
                if(length > waitingJob.occupy){
                    waitingJob.start = seg.first
                    waitingJob.end   = seg.second
                }
            }
        }
    }
}

fun firstInFirsOut(number:Int){
    initMemoryQueue(number)
    var curTime = 0
    while(jobQueue.isEmpty().not()){
        //检查是否可以加入作业处理队列
        val waitingJob = jobQueue.peek()
        processWorkingQueue(curTime)
    }
}

fun main(args:Array<String>){
    initMemoryQueue(5)

    val a = 10
}

