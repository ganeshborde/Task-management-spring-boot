package com.example.taskmanagement.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleProducer {

    public static void main(String[] args) {

        // 1. Create properties
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        // Optional but recommended
        props.put("acks", "all");

        // 2. Create producer
        KafkaProducer<String, String> producer =
                new KafkaProducer<>(props);

        // 3. Send message
        ProducerRecord<String, String> record =
                new ProducerRecord<>("test-topic", "101", "Hello Kafka");

        producer.send(record);

        System.out.println("Message sent successfully");

        // 4. Close producer
        producer.close();
    }
}