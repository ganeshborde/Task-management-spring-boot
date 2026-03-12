package com.example.taskmanagement.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {

    public static void main(String[] args) {

        // 1. Create properties
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        props.put("auto.offset.reset", "earliest");

        // 2. Create consumer
        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<>(props);

        // 3. Subscribe topic
        consumer.subscribe(Collections.singletonList("test-topic"));

        // 4. Poll messages
        while (true) {

            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {

                System.out.println("Key: " + record.key());
                System.out.println("Value: " + record.value());
                System.out.println("Partition: " + record.partition());
                System.out.println("Offset: " + record.offset());
                System.out.println("------------------------");
            }
        }
    }
}