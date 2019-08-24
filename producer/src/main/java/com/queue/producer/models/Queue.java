package com.queue.producer.models;

public class Queue {
    private String routingKey;
    private String queueName;
    private String message;

    public Queue() {
    }

    public Queue(String routingKey, String queueName, String message) {
        this.routingKey = routingKey;
        this.queueName = queueName;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
