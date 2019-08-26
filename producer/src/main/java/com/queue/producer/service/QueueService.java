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

    private ArrayList<Integer> stackSizes;
    private boolean change = false;
    private ArrayList<Object> tempMessages = new ArrayList<>();
    private BlockingQueue q;

    /**
     * Método que realiza el llamado a la simulación del funcionamiento de la cola.
     * Dependiendo el ambiente de trabajo. Para el caso de la prueba solo se maneja una cola
     * para los tres ambientes DevelopQueue, TestQueue, ProductionQueue.
     *
     * @param @Queue queue
     * @return
     */
    public boolean operationQueue(Queue queue) {
        String queueName = queue.getQueueName();
        int messagesSize = (queue != null && queue.getMessages() != null) ? queue.getMessages().size() : 0;
        int concurrenceSize = (queue != null && queue.getConcurrenceSize() > 0) ? queue.getMessages().size() : 0;
        if (concurrenceSize > 0 && messagesSize > 0 && (queue.getConcurrenceSize() <= queue.getMessages().size())) {
            stackSizes = evenOrOdd(messagesSize, queue.getConcurrenceSize());
            switch (queueName) {
                case "DevelopQueue":
                case "TestQueue":
                case "ProductionQueue":
                    queueSimulate(queue);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que permite gestionar los los tamaños de la cola.
     *
     * @param @Queue queue
     * @return @ArrayList<Integer> stackSizes
     */
    private ArrayList<Integer> evenOrOdd(int messagesSize, int concurrentSize) {
        ArrayList<Integer> stackSizes = new ArrayList<>();
        int size = 0;
        if (messagesSize > 1 && concurrentSize > 0) {
            if (messagesSize % concurrentSize == 0) {
                size = messagesSize / concurrentSize;
                stackSizes.add(size);
                change = true;
                return stackSizes;
            } else {
                size = messagesSize / concurrentSize;
                stackSizes.add(size);
                size = messagesSize % concurrentSize;
                stackSizes.add(size);
                return stackSizes;
            }
        } else {
            stackSizes.add(1);
            return stackSizes;
        }
    }

    /**
     * Método que permite gestionar el comportamiento de la cola, es decir
     * al llamar los metodos pairConcurrence, oddConcurrence e iniciar y cerrar
     * los hilos.
     *
     * @param @Queue queue
     * @return
     */
    private void queueSimulate(Queue queue) {
        q = new ArrayBlockingQueue(stackSizes.get(0));
        if (queue.getMessages().size() > 1) {
            if (change) {
                pairConcurrence(queue);
            } else {
                pairConcurrence(queue);
                oddConcurrence(queue);
            }
        } else {
            pairConcurrence(queue);
        }
    }

    /**
     * Método que permite evaluar el número concurrente de hilos par y llamar
     * tanto al productor como el consumidor.
     *
     * @param @Queue queue
     * @return
     */
    private void pairConcurrence(Queue queue) {
        q = new ArrayBlockingQueue(stackSizes.get(0));
        for (int i = 0; i < queue.getConcurrenceSize(); i++) {
            for (int j = stackSizes.get(0) - 1; j >= 0; j--) {
                if (!queue.getMessages().isEmpty()) {
                    tempMessages.add(queue.getMessages().get(j));
                    queue.getMessages().remove(j);
                } else {
                    break;
                }
            }
            Producer p = new Producer(stackSizes.get(0), q, tempMessages);
            Consumer c = new Consumer(q, tempMessages);
            Thread tp = new Thread(p);
            Thread tc = new Thread(c);
            tp.start();
            tc.start();
            tempMessages = new ArrayList<>();

        }
    }

    /**
     * Método que permite evaluar el número concurrente de hilos que faltaron
     * después de evaluar la concurrencia par  y llamar
     * tanto al productor como el consumidor.
     *
     * @param @Queue queue
     * @return
     */
    private void oddConcurrence(Queue queue) {
        q = new ArrayBlockingQueue(stackSizes.get(1));
        for (int i = 0; i < stackSizes.get(1); i++) {
            for (int j = stackSizes.get(1) - 1; j >= 0; j--) {
                if (!queue.getMessages().isEmpty()) {
                    tempMessages.add(queue.getMessages().get(j));
                    queue.getMessages().remove(j);
                } else {
                    break;
                }
            }
            Producer p = new Producer(stackSizes.get(1), q, tempMessages);
            Consumer c = new Consumer(q, tempMessages);
            Thread tp = new Thread(p);
            Thread tc = new Thread(c);
            tp.start();
            tc.start();
            tempMessages = new ArrayList<>();
        }
    }
}