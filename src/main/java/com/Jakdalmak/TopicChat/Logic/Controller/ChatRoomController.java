package com.Jakdalmak.TopicChat.Logic.Controller;

import com.Jakdalmak.TopicChat.Logic.Service.ChatRoomService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    /** 채팅방을 개설한다. kafkaTopicName은 카프카의 토픽이름 및 DB 내의 채팅방 id 값으로 사용된다. */
    @PostMapping("/chatroom/{kafkaTopicName}")
    public String makeChatRoomConnection(@PathVariable("kafkaTopicName")String topicName,
                                         @RequestParam("category")String category,
                                         @RequestParam("topic")String topic,
                                         @RequestParam("groupId")String groupId) {
        chatRoomService.makeChatRoom(topicName, groupId);

        return "goooood";
    }
}
