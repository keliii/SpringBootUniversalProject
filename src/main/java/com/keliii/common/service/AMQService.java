package com.keliii.common.service;

import com.keliii.common.config.AMQConfig;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.Map;

/**
 * Created by keliii on 2017/6/27.
 */
@Service
public class AMQService {

    @Autowired
    private Map<String, Queue> amqQueueMap;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(String queueName, String message) {
        Queue queue = amqQueueMap.get(queueName);
        if(queue == null) {
            queue = new ActiveMQQueue(queueName);
        }
        jmsMessagingTemplate.convertAndSend(queue, message);
    }


    @JmsListener(destination= AMQConfig.UNIVERSAL_TEST_MQ)
    public void listenerTest(String message) {
        System.out.println("###### "+ AMQConfig.UNIVERSAL_TEST_MQ +" ######");
        System.out.println("message:");
        System.out.println(message);
        System.out.println("###### "+ AMQConfig.UNIVERSAL_TEST_MQ +" end ######");
    }

}
