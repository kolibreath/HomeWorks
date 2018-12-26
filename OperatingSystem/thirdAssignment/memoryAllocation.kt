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

var memoryLength = 0
val random = Random()

//初始化待运算的作业队列
val jobQueue = LinkedList<JCB>()
//处理中的作业队列
val workingQueue = LinkedList<JCB>()

val memoryQueue = LinkedList<Pair<Int,Int>>()

fun initJobQueue(number: Int){
    //初始化第一次停止时间
    var lastStartTime = 0
    var lastEndTime:Int = (random.nextDouble()*100).toInt()
    //占用的内存空间
    var occupy :Int= (random.nextDouble()*100).toInt()
    repeat(number){
        val job = JCB(lastStartTime, lastEndTime,occupy)
        lastStartTime += (random.nextDouble() * 100).toInt()
        lastEndTime  = lastStartTime + (random.nextDouble()*100).toInt()
        occupy = (random.nextDouble()*100).toInt()
        jobQueue.add(job)
    }
}

//找出空闲的list
fun findSegmentation():LinkedList<Pair<Int,Int>>{

    val list = LinkedList<Pair<Int,Int>>()
   var counter= 0
    if(memoryQueue.size == 0) {
        list.add(Pair(0, memoryLength))
        return list
    }

    while(counter < memoryQueue.size){
        val thisOne = memoryQueue[counter]
        if(counter + 1 < memoryQueue.size){
            val nextOne = memoryQueue[counter + 1]
            val thisEnd = thisOne.first
            val nextStart = nextOne.second
            //确定出一个没有被使用的部分
            val pair = Pair(thisEnd,nextStart)
            list.add(pair)
        }
        counter ++
    }

    val lastJob = memoryQueue[--counter]
    if(lastJob.second != memoryLength )
        list.add(Pair(first = lastJob.second, second = memoryLength))

    return list
}



//process working queue & memoryQueue
//将已经完成的任务清除出去
fun processWorkingQueue(waitingJob:JCB){
    val cur = waitingJob.startTime
  for(job in workingQueue){
      if(cur > job.endTime){
          println("job completes! start time = ${job.startTime} end time = ${job.endTime} ")
          workingQueue.remove(job)
          for(memory in memoryQueue){
              if(memory.first == job.start && memory.second == job.end){
                  memoryQueue.remove(memory)
              }
          }
      }
  }
}

val firstInFirstOut : (job:JCB, segs:LinkedList<Pair<Int,Int>> ) -> Boolean = {
    job:JCB, segs:LinkedList<Pair<Int,Int>>->

    var flag = false
    for(seg in segs){
        val start = seg.first
        val end = seg.second
        if(end - start >= job.occupy){
            //从这里开始分配
            memoryQueue.add(Pair(first = start, second = job.occupy))
            workingQueue.add(JCB(startTime =  job.startTime, endTime = job.endTime,occupy = job.occupy).apply {
                this.start = start
                this.end = start + this.occupy
                flag = true
            })
            break
        }
    }
    flag
}

fun useAlgorithm(block: (
        job:JCB,
        segs:LinkedList<Pair<Int,Int>>)->Boolean){

    while(true ) {
        val waitingJob = jobQueue.peek()
        processWorkingQueue(waitingJob = waitingJob)
        val segs = findSegmentation()
        //调用相关的算法
        if (block(waitingJob, segs)) {
            //弹出这个job
            jobQueue.remove(waitingJob)

        }
        if(jobQueue.isEmpty()){
            println("所有任务都完成 正常结束！")
            return
        }
    }
}

fun main(args:Array<String>){
    val number = 5
    initJobQueue(number)
    memoryLength = 2* 100
    jobQueue.forEachIndexed {
        index, it->
        println("the $index job")
        println("the startTime of job : ${it.startTime}")
        println("the endTime of job : ${it.endTime}")
        println("the occupy ${it.occupy}")
    }

    println("开始执行算法")
    useAlgorithm(firstInFirstOut)
}
