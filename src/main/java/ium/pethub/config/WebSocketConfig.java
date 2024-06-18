package ium.pethub.config;

import ium.pethub.util.chat.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    private final ChatHandler chatHandler;

//    @Bean
//    public WebSocketInterceptor webSocketInterceptor(){
//        return new WebSocketInterceptor();
//    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");
             //   .addInterceptors(new WebSocketInterceptor());

    }

}
