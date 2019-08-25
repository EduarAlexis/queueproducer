package com.queue.producer.dao;

import com.queue.producer.models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private BlockingQueue blockingQueue;
    private AtomicInteger seq;
    private ArrayList<Object> messages;
    private int stackSize;
    private Logger logger = LoggerFactory.getLogger(Producer.class);

    public Producer(int stackSize, BlockingQueue blockingQueue, ArrayList<Object> messages) {
        this.stackSize = stackSize;
        this.blockingQueue = blockingQueue;
        this.seq = new AtomicInteger(0);
        this.messages = messages;
    }

    public void run() {
        try {
            for (int i = 0; i < stackSize; i++) {
                blockingQueue.put(producingMessages(messages.get(i)));
                Thread.sleep(10);
            }
        } catch (InterruptedException ex) {
            logger.info(ex.getMessage());
        }
    }

    private Book producingMessages(Object message) {
        Book book = new Book(seq);
        logger.info("Put: "+ book.toString()+ " - message "+ message.toString());
        return book;
    }
}