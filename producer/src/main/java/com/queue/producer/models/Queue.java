package com.queue.producer.models;

import java.util.ArrayList;

public class Queue {
    private int concurrenceSize;
    private String routingKey;
    private String queueName;
    private ArrayList<Object> messages;

    public Queue() {
    }

    public Queue(String routingKey, String queueName, ArrayList<Object> messages) {
        this.routingKey = routingKey;
        this.queueName = queueName;
        this.messages = messages;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getConcurrenceSize() {
        return concurrenceSize;
    }

    public void setConcurrenceSize(int concurrenceSize) {
        this.concurrenceSize = concurrenceSize;
    }

    public ArrayList<Object> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Object> messages) {
        this.messages = messages;
    }
}
