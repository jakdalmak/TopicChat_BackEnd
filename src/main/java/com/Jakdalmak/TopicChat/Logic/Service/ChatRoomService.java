package com.Jakdalmak.TopicChat.Logic.Service;


import com.Jakdalmak.TopicChat.Logic.Manager.KafkaListenerManager;
import com.Jakdalmak.TopicChat.Logic.Manager.KafkaTopicManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatRoomService {

    private final KafkaListenerManager kafkaListenerManager;
    private final KafkaTopicManager kafkaTopicManager;

    public ChatRoomService(KafkaListenerManager kafkaListenerManager, KafkaTopicManager kafkaTopicManager) {
        this.kafkaListenerManager = kafkaListenerManager;
        this.kafkaTopicManager = kafkaTopicManager;
    }

    /** 채팅방을 만드는데 필요한 관련 기반작업을 수행한다. */
    public void makeChatRoom(String topicName, String groupId) {

        kafkaTopicManager.createTopic(topicName, 1, (short) 1);
        kafkaListenerManager.registerKafkaListener(topicName, groupId);

    }

    public void sendMessageToUsers() {

    }
}
