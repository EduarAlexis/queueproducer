package com.queue.producer.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue queue;
    private ArrayList<Object> messages;
    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    public Consumer(BlockingQueue q, ArrayList<Object> messages) {
        this.queue = q;
        this.messages = messages;
    }

    public void run() {
        try {
            int i=0;
            while (true) {
                consume(queue.take(),messages.get(i));
                Thread.sleep(10);
                i++;
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    void consume(Object x, Object message) {
        logger.info("Get " + x.toString()+ "- Message"+ message.toString());
    }
}