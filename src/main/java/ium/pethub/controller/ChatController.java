package ium.pethub.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ium.pethub.domain.entity.ChatRoom;
import ium.pethub.dto.common.ResponseDto;
import ium.pethub.service.ChatService;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    @ValidToken
    @PostMapping("/api/create/chat-room/{targetId}")
    public ResponseEntity<?> createRoom(@PathVariable Long targetId) {

        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 생성되었습니다.",
                chatService.createRoom(UserContext.userData.get().getUserId(), targetId)

        ));
    }

    @ValidToken
    @GetMapping("/api/chat-room/list")
    public ResponseEntity<?> getChatRoomList() {
        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅방이 성공적으로 조회되었습니다.",
                chatService.getChatRoomList(UserContext.userData.get().getUserId())
        ));
    }

    @ValidToken
    @GetMapping("/api/chat/list/{roomId}")
    public ResponseEntity<?> getChatList(@PathVariable Long roomId) {

        return ResponseEntity.ok().body(ResponseDto.of(
                "채팅 목록이 성공적으로 조회되었습니다.",
                chatService.getChatList(roomId)
        ));
    }

    // 조훈창 - 수정
    // targetId에 해당하는 채팅방이 있으면 roomId도 반환
    @ValidToken
    @GetMapping("/api/exist/chat/{targetId}")
    public ResponseEntity<?> existChatRoom(@PathVariable Long targetId) {
        Map<String, Object> response = new HashMap<>();
        ChatRoom chatRoom = chatService.getChatRoom(UserContext.userData.get().getUserId(), targetId);
        if (chatRoom != null) {
            response.put("status", true);
            response.put("roomId",chatRoom.getId());
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));
        }
        response.put("status", false);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));

    }

    @ValidToken
    @GetMapping("/api/exist/chat-alarm")
    public ResponseEntity<?> messageAlarm() {
        Map<String, Boolean> response = new HashMap<>();
        if (chatService.existChatMessage(UserContext.userData.get().getUserId())) {
            response.put("alarm", true);
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));
        }
        response.put("alarm", false);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, response));
    }

//    @PutMapping("api/update/chat-room/{roomId}")
//    public ResponseEntity<?> updateTest(@PathVariable Long roomId) {
//
//        return ResponseEntity.ok().body(chatService.stateUpdate(roomId));
//    }
}
