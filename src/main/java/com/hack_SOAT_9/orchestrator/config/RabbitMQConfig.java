package com.hack_SOAT_9.orchestrator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String VIDEO_UPLOAD_QUEUE = "video.upload";
    public static final String VIDEO_UPLOAD_EXCHANGE = "video.upload";

    @Bean
    public Queue videoUploadQueue() {
        return new Queue(VIDEO_UPLOAD_QUEUE, true);
    }

    @Bean
    public DirectExchange videoUpload() {
        return new DirectExchange(VIDEO_UPLOAD_EXCHANGE);
    }

    @Bean
    public Binding videoBinding(Queue videoUploadQueue, DirectExchange videoUploadExchange) {
        return new Binding(
                videoUploadQueue.getName(),
                Binding.DestinationType.EXCHANGE,
                videoUploadExchange.getName(),
                videoUploadQueue.getName(),
                null
        );
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}