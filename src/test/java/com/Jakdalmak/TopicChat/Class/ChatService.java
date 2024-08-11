package com.Jakdalmak.TopicChat.Class;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.stereotype.Service;

import java.util.*;

public class ChatService {

    public ChatService(Queue<String> chattingQueue) {
        this.chattingQueue = chattingQueue;
    }


    //    @PostConstruct
//    public void setQueue() {
//        chattingQueue = new ArrayDeque<>();
//    }

    private Queue<String> chattingQueue;

    public String getChatingFromKafka() {
        if(chattingQueue.size() > 0) return chattingQueue.poll();
        else return "없어용";
    }

    public int ChatingToKafka(String chat) {
        chattingQueue.add(chat);

        return chattingQueue.size();
    }
}
