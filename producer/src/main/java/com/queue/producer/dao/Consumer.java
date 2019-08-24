package com.queue.producer.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue queue;
    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    public Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                consume(queue.take());
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    void consume(Object x) {
        logger.info("Take %s %s", x, System.lineSeparator());
    }
}