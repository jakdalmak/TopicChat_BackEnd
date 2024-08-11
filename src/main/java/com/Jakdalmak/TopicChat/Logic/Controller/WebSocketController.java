package com.Jakdalmak.TopicChat.Logic.Controller;


import com.Jakdalmak.TopicChat.Logic.DTO.MessagePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    /** 특정 경로 구독 인원이 메시지를 보낸 경우 */
    @MessageMapping("/Send/{kafkaTopicName}")
    public void sendMessageToKafka(@DestinationVariable("kafkaTopicName")String topicName,
                                   @Payload MessagePayload payload) {
        log.info("잘왔네요!" + payload.getMessage());
        kafkaTemplate.send(topicName, payload.getTempName() + "_" + payload.getMessage());
    }

}
