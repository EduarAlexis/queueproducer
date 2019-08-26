package com.queue.producer.dao;

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

    /**
     * Método que apila y muestra los mensajes.
     * Se toma un tiempo de 10 milisegundos para simular la velocidad del internet.
     *
     * @param
     * @return
     */
    public void run() {
        try {
            for (int i = 0; i < stackSize; i++) {
                if (!messages.isEmpty()) {
                    blockingQueue.put(producingMessages(messages.get(i)));
                    Thread.sleep(10);
                } else {
                    break;
                }
            }
        } catch (InterruptedException ex) {
            logger.info(ex.getMessage());
        }
    }

    /**
     * Método que muestra los mensajes consumidos.
     *
     * @param @Object message
     * @return
     */
    private String producingMessages(Object message) {
        logger.info("Put - message " + message.toString());
        return message.toString();
    }
}