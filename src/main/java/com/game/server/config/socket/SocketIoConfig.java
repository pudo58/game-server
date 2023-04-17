package com.game.server.config.socket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "socketio")
@Getter@Setter
@Primary
public class SocketIoConfig {
    private Integer port;
    private String host;
}
