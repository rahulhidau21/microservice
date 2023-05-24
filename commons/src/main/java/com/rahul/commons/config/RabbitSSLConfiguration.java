package com.rahul.commons.config;

import com.rabbitmq.client.ConnectionFactory;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitSSLConfiguration {
    @Value("${spring.rabbitmq.ssl:}")
    private String ssl;
    @Value("${spring.rabbitmq.host:localhost}")
    private String host;
    @Value("${spring.rabbitmq.port:5672}")
    private String port;
    @Value("${spring.rabbitmq.username:guest}")
    private String username;
    @Value("${spring.rabbitmq.password:guest}")
    private String password;
    @Value("${spring.rabbitmq.requested-heartbeat:60}")
    private int heartBeatIntervalSeconds;
    @Value("${rabbitmq.network.recovery.interval.milliseconds:5000}")
    private long networkRecoveryIntervalMilliseconds;
    @Value("${spring.rabbitmq.connection-timeout:5000}")
    private int connectTimeoutMilliseconds;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RabbitSSLConfiguration() {
        // TODO document why this constructor is empty
    }

    @Bean
    public AbstractConnectionFactory rabbitSslConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setHost(this.host);
        connectionFactory.setPort(Integer.parseInt(this.port));
        connectionFactory.setRequestedHeartbeat(this.heartBeatIntervalSeconds);
        connectionFactory.setNetworkRecoveryInterval(this.networkRecoveryIntervalMilliseconds);
        connectionFactory.setConnectionTimeout(this.connectTimeoutMilliseconds);

        if (StringUtils.isNotBlank(this.ssl)) {
            try {
                connectionFactory.useSslProtocol(this.ssl);
            } catch (Exception var4) {
                this.logger.error("Unable to use SSL for RabbitMQ", var4);
            }
        }

        return new CachingConnectionFactory(connectionFactory);
    }
}

