package com.queue.producer.service;

import com.queue.producer.dao.Consumer;
import com.queue.producer.dao.Producer;
import com.queue.producer.models.Queue;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class QueueService {

    public boolean operationQueue(Queue queue) throws InterruptedException {
        String queueName = queue.getQueueName();
        Thread t1 = null;
        Thread t2 = null;
        BlockingQueue q = new ArrayBlockingQueue(10);
        switch (queueName) {
            case "DevelopQueue":
                t1 = new Thread(new Producer(q));
                t2 = new Thread(new Consumer(q));
                t1.start();
                t2.start();
                t1.join();
                t2.join();
                break;
            case "TestQueue":
                t2 = new Thread(new Consumer(new ArrayBlockingQueue(5)));
                t2.start();
                break;
            case "ProductionQueue":
                Thread t3 = new Thread(new Consumer(new ArrayBlockingQueue(30)));
                t3.start();
                t3.join();
                break;
        }
        return true;
    }
}
