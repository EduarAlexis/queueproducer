package com.queue.producer.service;

import com.queue.producer.dao.Consumer;
import com.queue.producer.dao.Producer;
import com.queue.producer.models.Queue;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service
public class QueueService {

    public boolean operationQueue(Queue queue) throws InterruptedException {
        String queueName = queue.getQueueName();
        switch (queueName) {
            case "DevelopQueue":
                Thread t1 = new Thread(new Producer(new ArrayBlockingQueue(10)));
                t1.start();
                t1.join();
                break;
            case "TestQueue":
                Thread t2 = new Thread(new Consumer(new ArrayBlockingQueue(20)));
                t2.start();
                t2.join();
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
