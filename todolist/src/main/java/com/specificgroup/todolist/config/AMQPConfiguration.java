
package com.specificgroup.todolist.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Slf4j
@Configuration
public class AMQPConfiguration {

    @Bean
    public TopicExchange taskTopicExchange(@Value("${amqp.exchange.task}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue TaskQueue(
            @Value("${amqp.queue.task}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding correctAttemptsBinding(final Queue taskQueue,
                                          final TopicExchange taskExchange) {
        return BindingBuilder.bind(taskQueue)
                .to(taskExchange)
                .with("task.done");
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new
                DefaultMessageHandlerMethodFactory();
        final MappingJackson2MessageConverter jsonConverter =
                new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(
                new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(
            final MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }
}
