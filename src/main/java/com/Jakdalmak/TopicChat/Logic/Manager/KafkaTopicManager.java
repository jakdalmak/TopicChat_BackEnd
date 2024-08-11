package com.Jakdalmak.TopicChat.Logic.Manager;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaTopicManager {

    private final KafkaAdmin kafkaAdmin;

    public KafkaTopicManager(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    /** adminClient 객체를 이용하여, 어플리케이션 실행 중에 토픽을 동적으로 생성 가능 */
    public void createTopic(String topicName, int numPartitions, short replicationFactor) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {

            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);

            // 여러 개의 토픽을 Collection으로 받아 동시에 생성 가능한 기능이나, 본 기능은 하나의 토픽을 일일히 개설하는 것을 목적으로 한다.
            // 이에 따라 singleton을 사용하여 하나만 생성할 것임을 명시??
            CreateTopicsResult result = adminClient.createTopics(java.util.Collections.singleton(newTopic));
            result.all().get(); // 비동기 작업의 결과를 대기하여 토픽 생성이 완료될때까지 코드 진행 블락

            System.out.println("Topic created: " + topicName);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
