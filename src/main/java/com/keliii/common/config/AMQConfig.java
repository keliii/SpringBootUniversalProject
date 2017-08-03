package com.keliii.common.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by keliii on 2017/6/27.
 */
@Configuration
public class AMQConfig {

    public final static String UNIVERSAL_TEST_MQ = "UNIVERSAL_TEST_MQ";

    public final static String UNIVERSAL_DEV_MQ = "UNIVERSAL_DEV_MQ";

    @Bean
    public Map<String, Queue> ampQueueMap() {
        Map<String,Queue> queueMap = new HashMap<>();
        queueMap.put(UNIVERSAL_TEST_MQ,new ActiveMQQueue(UNIVERSAL_TEST_MQ));
        queueMap.put(UNIVERSAL_DEV_MQ,new ActiveMQQueue(UNIVERSAL_DEV_MQ));
        return queueMap;
    }

}
