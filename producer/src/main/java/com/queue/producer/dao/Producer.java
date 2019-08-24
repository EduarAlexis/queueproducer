package com.queue.producer.dao;

import com.queue.producer.models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final BlockingQueue queue;
    private AtomicInteger seq;
    Logger logger = LoggerFactory.getLogger(Producer.class);

    public Producer(BlockingQueue q) {
        queue = q;
        seq = new AtomicInteger(0);
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                queue.put(produce());
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private Book produce() {
        Book book = new Book(seq);
        logger.info("Put %s %s", book, System.lineSeparator());
        return book;
    }
}