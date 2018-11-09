# 多线程下面的几种模型


## 什么是PV 操作：
p操作不仅仅是-1 而已 还有一个等待和重新尝试的过程：
[PV操作](https://www.zhihu.com/question/270104292/answer/352476996)

[所有的并发模型](https://github.com/chyyuu/os_course_exercises/blob/master/all/07-2-spoc-pv-problems.md)
# definition
死锁的一般定义：若在一个进程集合中，每一个进程都在等待一个永远不会发生的事件而形成一个永久的阻塞状态，这种阻塞状态就是死锁。


## 生产者消费者模型
[生产者消费者模型](https://zh.wikipedia.org/wiki/%E7%94%9F%E4%BA%A7%E8%80%85%E6%B6%88%E8%B4%B9%E8%80%85%E9%97%AE%E9%A2%98)


错误的做法：

```
int itemCount = 0;

procedure producer() {
    while (true) {
        item = produceItem();
        if (itemCount == BUFFER_SIZE) {
            sleep();
        }
        putItemIntoBuffer(item);
        itemCount = itemCount + 1;
        if (itemCount == 1) {
            wakeup(consumer);
        }
    }
}

procedure consumer() {
    while (true) {
        if (itemCount == 0) {
            sleep();
        }
        item = removeItemFromBuffer();
        itemCount = itemCount - 1;
        if (itemCount == BUFFER_SIZE - 1) {
            wakeup(producer);
        }
        consumeItem(item);
    }
}
```

错误原因：
消费者消费完最后一个item，还没有睡的时候交给生产者，生产者生产 然后唤醒消费者，但是消费者是醒着的，然后直接进入下面的睡觉代码，然后生产者一直生产，直到睡着。

没有使用两个状态标记，会进入错误的状态。
        
```
semaphore mutex = 1;
semaphore fillCount = 0;
semaphore emptyCount = BUFFER_SIZE;

procedure producer() {
    while (true) {
        item = produceItem();
        down(emptyCount);
            down(mutex);
                putItemIntoBuffer(item);
            up(mutex);
        up(fillCount);
    }
}
procedure consumer() {
    while (true) {
        down(fillCount);
            down(mutex);
                item = removeItemFromBuffer();
            up(mutex);
        up(emptyCount);
        consumeItem(item);
    }
}
```

多个线程作为生产者和消费者
注意mutex和empcount 或者 fillcount顺序不能随便切换（没有生产的位置或者没有消费的产物就不要随便占用进程）

- java 多线程实现 生产者消费者模型

可重入锁也就是说如果一个线程外部被锁上了，但是内部的代码需要相同的锁，外部的锁不需要释放内部的代码也可以执行
[可重入锁](https://www.jianshu.com/p/007bd7029faf)

- 用Kotlin 如何实现呢？

[blocking queue](http://www.infoq.com/cn/articles/java-blocking-queue)

## 读者写者模型

List<TreeNode> list = new ArrayList<>());
Treenode o = new Treenode();
list.add(o)

o = null;
list.get(0) = null;

# 理发师问题：
[一个理发师文日ok的解法](https://blog.csdn.net/duanzhengbing/article/details/52141699)