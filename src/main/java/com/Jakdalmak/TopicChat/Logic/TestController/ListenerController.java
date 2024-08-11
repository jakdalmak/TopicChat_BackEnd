package com.Jakdalmak.TopicChat.Logic.TestController;

import com.Jakdalmak.TopicChat.Logic.TestService.ListenerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/** 채팅방에 진입하거나 나간 경우 kafka의 특정 Topic에 대한 consume 수행 */
@RestController
public class ListenerController {

    private final ListenerService listenerService;

    public ListenerController(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    /** 카프카 내에 토픽을 생성하고, 토픽에 대한 코드를 작성한다. */
    @GetMapping("/test/{topicName}")
    public String setTopicAndListener(@PathVariable("topicName")String topicName) {
        return listenerService.setNewKafkaListener(topicName, UUID.randomUUID().toString());
    }

}
