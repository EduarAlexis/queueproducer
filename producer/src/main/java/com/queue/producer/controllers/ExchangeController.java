package com.queue.producer.controllers;

import com.queue.producer.models.Queue;
import com.queue.producer.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

    @Autowired
    QueueService queueService;

    @RequestMapping(value = "/simulatequeue", method = RequestMethod.POST)
    public ResponseEntity<String> publishMessage(@RequestBody Queue queue) throws InterruptedException {
        boolean option = false;
        option = queueService.operationQueue(queue);
        queue.setQueueName("TestQueue");
        option = queueService.operationQueue(queue);
        if (option) {
            return new ResponseEntity<>("Publish message succesfull", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Publish message succesfull", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
