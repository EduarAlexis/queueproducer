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
                Thread.sleep(10);
            }
        } catch (InterruptedException ex) {
            logger.info(ex.getMessage());
        }
    }

    private Book produce() {
        Book book = new Book(seq);
        logger.info("Put "+ book.toString()+" - "+System.lineSeparator());
        return book;
    }
}