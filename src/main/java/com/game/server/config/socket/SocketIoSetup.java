package com.game.server.config.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SocketIoConfig.class)
public class SocketIoSetup {
    @Autowired
    private SocketIoConfig config;

    @Bean
    public SocketIOServer socketIoServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(this.config.getHost());
        config.setPort(this.config.getPort());
        return new SocketIOServer(config);
    }
}
