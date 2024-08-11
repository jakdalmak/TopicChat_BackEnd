package com.Jakdalmak.TopicChat.Config.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@EnableKafka // Kafka 리스너를 활성화하기 위해 사용합니다. Kafka 리스너를 사용하려면 이 어노테이션이 필요합니다.
@Configuration // 이 클래스가 Spring의 Configuration 클래스임을 명시합니다. 이 클래스는 Bean을 정의하는 데 사용됩니다.
public class KafkaConfig {

    private final String bootstrapServers = "43.203.218.229:9092"; // Kafka 서버의 주소를 설정합니다.

    // Kafka Producer 설정을 위한 Bean을 정의합니다.
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        // Kafka Producer에 필요한 설정을 Map에 담습니다.
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Kafka 서버 주소를 설정합니다.
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 메시지의 key를 직렬화하기 위한 Serializer를 설정합니다.
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 메시지의 value를 직렬화하기 위한 Serializer를 설정합니다.
        // ProducerFactory는 Kafka Producer 인스턴스를 생성하는 역할을 합니다.
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // KafkaTemplate을 정의합니다. 이 Bean을 통해 Kafka로 메시지를 보낼 수 있습니다.
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory()); // ProducerFactory를 사용하여 KafkaTemplate을 생성합니다.
    }

    // Kafka Consumer 설정을 위한 Bean을 정의합니다.
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Kafka Consumer에 필요한 설정을 Map에 담습니다.
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Kafka 서버 주소를 설정합니다.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "default-group"); // Consumer 그룹 ID를 설정합니다. Kafka는 동일한 그룹 ID를 가진 Consumer들이 같은 메시지를 처리하지 않도록 보장합니다.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 메시지의 key를 역직렬화하기 위한 Deserializer를 설정합니다.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 메시지의 value를 역직렬화하기 위한 Deserializer를 설정합니다.
        // ConsumerFactory는 Kafka Consumer 인스턴스를 생성하는 역할을 합니다.
        return new DefaultKafkaConsumerFactory<>(props);
    }

    // Kafka 리스너 컨테이너를 생성하기 위한 Bean을 정의합니다.
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        // 리스너 컨테이너 팩토리를 생성합니다.
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory()); // 앞에서 정의한 ConsumerFactory를 설정합니다.
//        return factory; // 이 팩토리를 통해 Kafka 리스너 컨테이너가 생성되고 메시지를 처리합니다.
//    }

    // 동적 리스너를 등록할 때 사용할 KafkaListenerEndpointRegistry를 정의합니다.
//    @Bean
//    public KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry() {
//        return new KafkaListenerEndpointRegistry(); // Kafka 리스너를 동적으로 등록/관리하는 레지스트리입니다.
//    }

    // 동적 Kafka 리스너를 위한 KafkaListenerContainerFactory를 정의합니다.
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactoryForDynamic() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory()); // ConsumerFactory를 설정합니다.
        factory.setRecordMessageConverter(new StringJsonMessageConverter()); // 메시지를 JSON 형식으로 변환하는 MessageConverter를 설정합니다.
        return factory; // 이 팩토리를 사용하여 동적 Kafka 리스너 컨테이너를 생성할 수 있습니다.
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>(); // KafkaAdmin 설정을 위한 설정 맵 생성
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "43.203.218.229:9092"); // 카프카 브로커 주소 설정
        return new KafkaAdmin(configs); // KafkaAdmin 객체 생성 및 반환
    }

}