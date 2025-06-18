package ru.eremin.restwithspringjpa.kafka;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@AllArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String messageKey, String messageValue) {
        log.info("sending message='{}' to topic='topic-1'", messageValue);
        kafkaTemplate.send("topic-1", messageKey, messageValue);
    }
}
