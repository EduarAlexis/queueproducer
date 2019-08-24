package com.queue.producer.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Book {
    Integer id;
    private static AtomicInteger seq;

    public Book(AtomicInteger seq) {
        this.seq = seq;
        this.id = seq.incrementAndGet();
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}