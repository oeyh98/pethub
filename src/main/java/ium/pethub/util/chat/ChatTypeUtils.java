//package ium.pethub.util.chat;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import ium.pethub.domain.entity.MessageEnum;
//import ium.pethub.dto.chat.request.ChatAckDto;
//import ium.pethub.dto.chat.request.ChatMessageDto;
//import ium.pethub.dto.chat.request.EnterTypeDto;
//import ium.pethub.dto.chat.response.ChatResponseDto;
//import ium.pethub.dto.chat.response.SocketStatus;
//import ium.pethub.service.ChatService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class ChatTypeUtils {
//
//    private final ObjectMapper objectMapper;
//
//    private final ChatService chatService;
//
//
//    public void EnterTypeProcess(String payload, WebSocketSession session, Map<Long,WebSocketSession> userSocketList) throws JsonProcessingException {
//        EnterTypeDto data = objectMapper.readValue(payload, EnterTypeDto.class);
//        userSocketList.put(data.getUserId(), session);
//    }
//
//    public void MessageTypeProcess(String payload, Map<Long, WebSocketSession> userSocketList) throws IOException {
//        ChatMessageDto chat = objectMapper.readValue(payload, ChatMessageDto.class);
//        ChatResponseDto responseDto = null;
//        SocketStatus socketStatus = new SocketStatus(400, "duplicateError", MessageEnum.MESSAGE);
//        try {
//            responseDto = chatService.saveMessage(chat);
//        } catch (DuplicateKeyException e) {
//            userSocketList.get(chat.getSenderId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(socketStatus)));
//        }
//        if (userSocketList.get(chat.getRecipientId()) != null) {
//             userSocketList.get(chat.getRecipientId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDto)));
//        }
//
//        // 조훈창 수정 -> 메세지 보낸 사람에게도 같은 내용을 보내줌 , 이유: 클라이언트에서 보낸 메세지가 정상적으로 송수신 되었는지에 대한 판단 여부를 서버측의 오류가 없으면 정상으로 인시하고, 서버가 수신한 내용을 클라이언트측에 보내 줌으로서 다시 표시한다.
//        if (userSocketList.get(chat.getSenderId()) != null) {
//             userSocketList.get(chat.getSenderId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDto)));
//        }
//    }
//
//    public void ACKTypeProcess(String payload,Map<Long, WebSocketSession> userSocketList) throws IOException {
//
//        SocketStatus socketStatus = new SocketStatus(200, "true",MessageEnum.ACK);
//        ChatAckDto ack = objectMapper.readValue(payload, ChatAckDto.class);
//        log.info("보낼사람 : {}" ,  ack.getSenderId());
//        if(chatService.stateUpdate(ack.getChatId())) {
//            userSocketList.get(ack.getSenderId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(socketStatus)));
//        }
//
//    }
//}
