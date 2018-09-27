import java.util.*

const val wait = 0
const val run = 1
const val finish = 2

var jobQueue: Queue<JCB> = LinkedList<JCB>()

class JCB constructor(val name:String,
                      //开始时间 format 10:00
                      val handinTime:String,
                      // 表示多少分钟(预计的完成时间)
                      val worktime:Int,
                      //实际的完成时间：
                      val realWorkTime:Int = 0,
                      //带权周转时间
                      val weight :Int = 0 ,
                      //实际的完成时间
                      val finishTime :String= "10:00",
                      val resource:String,
                      val status:Int,
                      val jobQueue: Queue<JCB>){
}

/**
 * 计算开始时间到工作完成的时间 获取工作完成的时间的时间
 * workTime = 周转时间 = 预计工作时间 + 实际等待时间
 */
fun elapse(handinTime: String, worktime: Int):String{
    val min = handinTime.split(":")[1].toInt()
    val hour = handinTime.split(":")[0].toInt()
    val elapsedTime = hour*60  + min + worktime
    val elapsedHour = elapsedTime/60
    val elapsedMin = elapsedTime%60

    val endHour = ((elapsedHour)%24)
    val endmin  = ( elapsedMin)
    return "${String.format("%02d",endHour)}:${String.format("%2d",endmin)}"
}

/**
 * 计算作业提交的时间 和 作业 完成的时间的差值 返回值是周转时间的一部分 也就是
 * 在格式化转换的时候需要删去前导或者后面链接着的空格
 */

fun elapse(handinTime: String, startTime:String):Int{
    val startMin = startTime.split(":")[1].trimStart().toInt()
    val startHour = startTime.split(":")[0].trimStart().toInt()

    val handinMin = handinTime.split(":")[1].trimStart().toInt()
    val handinHour= handinTime.split(":")[0].trimStart().toInt()

    val elapsedMins = startMin - handinMin + (startHour - handinHour)*60


    return elapsedMins
}

/**
 * 填一些数值到一些作业中去
 */
fun JCB.getCompleteJobs(realWorkTime: Int,weight: Int,finishTime: String):JCB {
    return JCB(name = this.name,
            handinTime = this.handinTime,
            worktime = this.worktime,
            realWorkTime = realWorkTime,
            weight =  weight,
            finishTime = finishTime,
            resource =  this.resource,
            status = finish,
            jobQueue = jobQueue)
}
/**
 * 单道程序的先来先服务算法
 * 假设开始的时间basetime 是 10:00
 */
fun fcfs(jobQueue: Queue<JCB>):LinkedList<JCB>{
    var startTime = jobQueue.first().handinTime
    var finishJobQueue = LinkedList<JCB>()
    for(job in jobQueue){
        val elapsedWaitingTime = elapse(job.handinTime,startTime)
        val finTime =  elapse(job.handinTime,job.worktime + elapsedWaitingTime)
        startTime = finTime

        val finJob = job.getCompleteJobs(
                realWorkTime =  job.worktime + elapsedWaitingTime,
                weight = (job.worktime + elapsedWaitingTime)/job.worktime,
                finishTime = finTime
        )

        finishJobQueue.add(finJob)
    }
    return finishJobQueue
}

/**
 * 最短作业优先算法：扫描有没有在基准时间之前到达的任务，如果有： 加入list，扫描这个list 找出最短的job 完成后pop出作业队列
 */
fun sjfs(jobQueue: Queue<JCB>):LinkedList<JCB>{
    var startTime = jobQueue.first().handinTime

    val finJobs = LinkedList<JCB>()

    while(!jobQueue.isEmpty()){
    //将开始时间之前已经到达的作业放入list 中
        val alreadyComeList = LinkedList<JCB>()
        for(job in jobQueue){
            if(elapse(job.handinTime,startTime)>=0)
                alreadyComeList.add(job)
        }

        //选出在队列中最短的时间的作业
        var shortest = alreadyComeList[0].worktime
        var dueJob = alreadyComeList[0]
        for(job in alreadyComeList){
            if(shortest > job.worktime){
                shortest = job.worktime
                dueJob = job
            }
        }

        val elapsedWaitingTime = elapse(dueJob.handinTime,startTime)
        val finTime =  elapse(dueJob.handinTime,dueJob.worktime + elapsedWaitingTime)
        startTime = finTime

        val finJob = dueJob.getCompleteJobs(
                realWorkTime =  dueJob.worktime + elapsedWaitingTime,
                weight =  (dueJob.worktime + elapsedWaitingTime)/dueJob.worktime,
                finishTime = finTime
        )
        finJobs.add(finJob)

        jobQueue.remove(dueJob)
    }

    return finJobs

}

/**
 * 最高响应比算法
 */

fun hrn(jobQueue:Queue<JCB>):LinkedList<JCB>{
    var startTime = jobQueue.first().handinTime
    val finJobs = LinkedList<JCB>()

    while(!jobQueue.isEmpty()){
        //将开始时间之前已经到达的作业放入list 中
        val alreadyComeList = LinkedList<JCB>()
        for(job in jobQueue){
            if(elapse(job.handinTime,startTime)>=0)
                alreadyComeList.add(job)
        }


        //获取最高响应比的任务
        fun geHighestHrn() :JCB{
            var dueJob = alreadyComeList[0]
            var highest = elapse(alreadyComeList[0].handinTime,startTime)/alreadyComeList[0].worktime
            for (job in alreadyComeList) {
                val currentHrn = elapse(job.handinTime, startTime)/job.worktime
                if(currentHrn > highest){
                    dueJob = job
                    highest = currentHrn
                }
            }

            return dueJob
        }

        val dueJob = geHighestHrn()
        val elapsedWaitingTime = elapse(dueJob.handinTime,startTime)
        val finTime =  elapse(dueJob.handinTime,dueJob.worktime + elapsedWaitingTime)
        startTime = finTime

        val finJob = dueJob.getCompleteJobs(
                realWorkTime =  dueJob.worktime + elapsedWaitingTime,
                weight =  (dueJob.worktime + elapsedWaitingTime)/dueJob.worktime,
                finishTime = finTime
        )
        finJobs.add(finJob)

        jobQueue.remove(dueJob)
    }
    return finJobs
}

/**
 * 初始化可能的作业队列
 */

fun initJobs():LinkedList<JCB> {

    val job1 = JCB(name = "job1", handinTime = "8:00", worktime = 120, realWorkTime = 0, weight = 0, finishTime = "10:00", resource = "CPU", status = wait, jobQueue = jobQueue)
    val job2 = JCB(name = "job2", handinTime = "8:50", worktime = 50, realWorkTime = 0, weight = 0, finishTime = "10:00", resource = "CPU", status = wait, jobQueue = jobQueue)
    val job3 = JCB(name = "job3", handinTime = "9:00", worktime = 10, realWorkTime = 0, weight = 0, finishTime = "10:00", resource = "CPU", status = wait, jobQueue = jobQueue)
    val job4 = JCB(name = "job4", handinTime = "9:50", worktime = 20, realWorkTime = 0, weight = 0, finishTime = "10:00", resource = "CPU", status = wait, jobQueue = jobQueue)

    return LinkedList<JCB>().apply {
        add(job1)
        add(job2)
        add(job3)
        add(job4)
    }
}



fun main(args:Array<String>){
     val finJobs = hrn(jobQueue = initJobs())
     for (job in finJobs)
         println(job.finishTime)
}
