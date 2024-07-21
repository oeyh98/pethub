//package ium.pethub.util.chat;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import ium.pethub.domain.entity.MessageEnum;
//import ium.pethub.dto.chat.request.MessageType;
//import ium.pethub.service.ChatService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Map;
//
//@Component
//@Slf4j
//@AllArgsConstructor
//public class ChatHandler extends TextWebSocketHandler {
//
//    private final Map<Long,WebSocketSession> userSocketList;
//    private final ChatService chatService;
//    private final ObjectMapper objectMapper;
//    private final ChatTypeUtils chatTypeUtils;
//
//    // WebSocket으로 받은 메시지 처리 로직
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload =  message.getPayload();
//        log.info("payload : {} " ,payload);
//
//        MessageEnum type = objectMapper.readValue(payload, MessageType.class).getType();
//
//        switch (type) {
//            case ENTER :
//                chatTypeUtils.EnterTypeProcess(payload,session,userSocketList);
//                break;
//
//            case MESSAGE:
//              chatTypeUtils.MessageTypeProcess(payload,userSocketList);
//                break;
//            case ACK:
//                chatTypeUtils.ACKTypeProcess(payload,userSocketList);
//                break;
//        }
//
//    }
//
//    //클라이언트가 연결 된 후
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//    //    userSocketList.put(UserContext.userData.get().getUserId(), session);
//        log.info(" 클라이언트 접속");
//     //   UserContext.remove();
//
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        for(Long key : userSocketList.keySet()){
//            if(userSocketList.get(key).equals(session)){
//                userSocketList.remove(key);
//            }
//        }
//    }
//}
