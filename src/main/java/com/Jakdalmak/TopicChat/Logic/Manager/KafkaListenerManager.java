package com.Jakdalmak.TopicChat.Logic.Manager;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class KafkaListenerManager {

    @PostConstruct
    public void postConstruct() {
        containers = new HashMap<>();
    }
    private Map<String, ConcurrentMessageListenerContainer<String, String>> containers;

    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactoryForDynamic;
    private final ConsumerFactory<String, String> consumerFactory;
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public KafkaListenerManager(SimpMessagingTemplate messagingTemplate, KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactoryForDynamic, ConsumerFactory<String, String> consumerFactory, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.kafkaListenerContainerFactoryForDynamic = kafkaListenerContainerFactoryForDynamic;
        this.consumerFactory = consumerFactory;
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    }

    // 동적 Kafka 리스너를 등록하는 메서드
    public void registerKafkaListener(String topic, String groupId) {
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(groupId);

        // 메시지 리스너 설정
        containerProps.setMessageListener((MessageListener<String, String>) record -> {
            String[] recordSplit = record.value().split("_");

            String userName = recordSplit[0];
            String message = recordSplit[1];

            messagingTemplate.convertAndSend("/subscribeTopic/" + topic, userName + "이 말하길, " + message);

            log.info("KafkaListenerManager - Received .2: " + record.value() + " from topic: " + topic);
        });

        // 리스너 컨테이너 생성
        ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(
                consumerFactory, containerProps);

        container.setConcurrency(3); // 리스너의 동시성 수준 설정
        container.start(); // 리스너 시작

        containers.put(topic, container); // 리스너를 관리한다.

//        kafkaListenerEndpointRegistry.registerListenerContainer(container, kafkaListenerContainerFactoryForDynamic, true);
    }

    public void resiginKafkaListener(String topic) {}

}
