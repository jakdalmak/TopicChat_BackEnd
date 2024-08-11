package com.Jakdalmak.TopicChat.Test;

import com.Jakdalmak.TopicChat.Class.ChatService;
import com.Jakdalmak.TopicChat.Class.TopicEnum;
import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@Tag("embedded-kafka-test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
public class ChatServiceTest {

//    @Autowired
    private ChatService chatService;
//
//    @Autowired
//    private EmbeddedKafkaZKBroker embeddedKafkaBroker = new EmbeddedKafkaZKBroker(
//            2,
//            true,
//            2,
//            String.valueOf(TopicEnum.CHAT_1)
//    );
//
//    private KafkaMessageListenerContainer<String, String> container;
//
//    private BlockingQueue<ConsumerRecord<String, String>> records;
//
//    private void configEmbeddedKafkaConsumer(String topic) {
//        Map<String, Object> consumerProperties = new HashMap<>(KafkaTestUtils.consumerProps("auth", "false", embeddedKafkaBroker));
//
//        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties);
//
//        ContainerProperties containerProperties = new ContainerProperties(topic);
//        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
//
//        records = new LinkedBlockingDeque<>();
//
//        container.setupMessageListener((MessageListener<String, String>) record -> {
//            Logger.debug("test-listener received message='{}'",
//                    record.value());
//            records.add(record);
//        });
//        container.start();
//
//        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
//    }
//
//    private Producer<String, Object> configEmbeddedKafkaProducer() {
//        Map<String, Object> producerProperties = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
//        return new DefaultKafkaProducerFactory<>(producerProperties, new StringSerializer(), new JsonSerializer<>()).createProducer();
//    }



    @BeforeEach
    public void 기본_설정() {
        chatService = new ChatService(new ArrayDeque<String>());
    }



    @Test
    public void 채팅_보내기() {
        int queueSize = chatService.ChatingToKafka("안녕하세요");
        assertTrue(queueSize > 0);
    }

    @Test
    public void 채팅_받기() {

        String Chat = chatService.getChatingFromKafka();

        assertEquals(Chat, "안녕하세요");
    }

    @Test
    public void 비어있는_채팅을_보낸_경우() {

    }

    @Test
    public void 비어있는_채팅을_받는_경우() {

    }


}
