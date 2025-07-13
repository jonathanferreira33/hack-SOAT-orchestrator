package com.hack_SOAT_9.orchestrator.config;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class RabbitMQConfigTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(RabbitMQConfig.class);

    @Test
    void shouldCreateQueueExchangeAndBindingBeans() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(Queue.class);
            assertThat(context).hasSingleBean(DirectExchange.class);
            assertThat(context).hasSingleBean(Binding.class);

            Queue queue = context.getBean(Queue.class);
            DirectExchange exchange = context.getBean(DirectExchange.class);
            Binding binding = context.getBean(Binding.class);

            assertThat(queue.getName()).isEqualTo(RabbitMQConfig.VIDEO_UPLOAD_QUEUE);
            assertThat(exchange.getName()).isEqualTo(RabbitMQConfig.VIDEO_UPLOAD_EXCHANGE);
            assertThat(binding.getDestination()).isEqualTo(queue.getName());
            assertThat(binding.getExchange()).isEqualTo(exchange.getName());
        });
    }
}
