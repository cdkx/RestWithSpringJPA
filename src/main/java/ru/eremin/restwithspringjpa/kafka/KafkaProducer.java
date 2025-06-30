package ru.eremin.restwithspringjpa.kafka;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.eremin.restwithspringjpa.model.dto.PostDTO;


@Slf4j
@AllArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, PostDTO> kafkaTemplate;


    public void sendMessage(PostDTO message) {
        log.info("sending message='{}' to topic='topic-1'", message);
        kafkaTemplate.send("topic-1", message);
    }
}
