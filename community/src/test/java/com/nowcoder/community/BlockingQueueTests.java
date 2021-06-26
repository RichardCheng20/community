package com.nowcoder.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//生产消费线程
public class BlockingQueueTests {
    public static void main(String[] args) {
        //先实例化共用的阻塞队列
        BlockingQueue queue = new ArrayBlockingQueue(10);
        new Thread(new Producer(queue)).start();//生产者
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();//并发消费
    }
}
//生产线程
class Producer implements Runnable {

    //实例化这个producer的时候要求调用方把阻塞队列传进来
    private BlockingQueue<Integer> queue;
    //有参构造器
    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            //循环放入到队列里
            for (int i = 0; i < 100; i++) {
                Thread.sleep(20);//停顿20ms
                queue.put(i);// i就是生产的数据
                System.out.println(Thread.currentThread().getName() + "生产：" + queue.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//消费线程
class Consumer implements Runnable {

    //实例化这个producer的时候要求调用方把阻塞队列传进来
    private BlockingQueue<Integer> queue;
    //有参构造器
    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            //有数据就消费
            while (true) {
                //调用key
                Thread.sleep(new Random().nextInt(1000));
                queue.take();//使用了
                System.out.println(Thread.currentThread().getName() + "消费：" + queue.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}