package com.Jakdalmak.TopicChat.Logic.TestService;

import com.Jakdalmak.TopicChat.Logic.Manager.KafkaListenerManager;
import org.springframework.stereotype.Service;

@Service
public class ListenerService {
    private final KafkaListenerManager kafkaListenerManager;

    public ListenerService(KafkaListenerManager kafkaListenerManager) {
        this.kafkaListenerManager = kafkaListenerManager;
    }

    // 주의! 리스너는 채팅방 내 사람의 수만큼 생성되는게 아니다. 리스너 만들고, 그 리스너에 대한 웹소켓 구독이 사람 수 만큼 있는거임!!
    /** 새로운 카프카 리스너를 생성한다. 이제 서버는 카프카로의 해당 토픽으로부터 리스닝을 수행하고 있다!!! */
    public String setNewKafkaListener(String topic, String groupId) {
        kafkaListenerManager.registerKafkaListener(topic, groupId);
        return groupId;
    }
}
