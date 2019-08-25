package com.queue.producer.service;

import com.queue.producer.dao.Consumer;
import com.queue.producer.dao.Producer;
import com.queue.producer.models.Queue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class QueueService {

    public void operationQueue(Queue queue) throws InterruptedException {
        String queueName = queue.getQueueName();
        int stackSize = queue.getMessages().size() / queue.getConcurrenceSize();
        switch (queueName) {
            case "DevelopQueue":
            case "TestQueue":
            case "ProductionQueue":
                queueSimulate(queue, new ArrayBlockingQueue(stackSize));
                break;
        }
    }

    private void queueSimulate(Queue queue, BlockingQueue q) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<Object> tempMessages = new ArrayList<>();
        int stackSize = queue.getMessages().size() / queue.getConcurrenceSize();
        for (int i = 0; i < queue.getConcurrenceSize(); i++) {
            for (int j = stackSize - 1; j >= 0; j--) {
                tempMessages.add(queue.getMessages().get(j));
                queue.getMessages().remove(j);
            }
            threads.add(new Thread(new Producer(stackSize, q, tempMessages)));
            threads.add(new Thread(new Consumer(q, tempMessages)));
            tempMessages = new ArrayList<>();
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }
}