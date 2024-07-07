package ium.pethub.service;

import ium.pethub.domain.entity.Chat;
import ium.pethub.domain.entity.ChatRoom;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.ChatRepository;
import ium.pethub.domain.repository.ChatRoomQueryRepository;
import ium.pethub.domain.repository.ChatRoomRepository;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.chat.request.ChatMessageDto;
import ium.pethub.dto.chat.response.ChatMessageListResponseDto;
import ium.pethub.dto.chat.response.ChatResponseDto;
import ium.pethub.dto.chat.response.ChatRoomResponseDto;
import ium.pethub.dto.chat.response.CreateRoomResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;
    private final ChatRoomQueryRepository chatRoomQueryRepository;

    public CreateRoomResponseDto createRoom(Long userId, Long targetId) {
        User user = userRepository.findById(userId).get();
        User targetUser = userRepository.findById(targetId).orElseThrow(
                () -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        ChatRoom chatRoom = new ChatRoom(user,targetUser);
        if(getChatRoom(userId,targetId)!= null){
            throw new  IllegalStateException("이미 채팅방이 존재합니다");
        }
        ChatRoom createRoom = chatRoomRepository.save(chatRoom);
         return new CreateRoomResponseDto(createRoom);
    }


    public List<ChatRoomResponseDto> getChatRoomList(Long userId) {
        return chatRoomQueryRepository.findAllByUserId(userId);
    }

    public ChatResponseDto saveMessage(ChatMessageDto chat) {
        ChatRoom chatRoom = getChatRoom(chat.getSenderId(), chat.getRecipientId());
        Chat chatMessage = Chat.builder().id(chat.getChatId()).chatRoom(chatRoom).senderId(chat.getSenderId()).recipientId(chat.getRecipientId()).content(chat.getContent()).state(false).build();
        System.out.println(chatMessage.getId());
        Chat chatResponse = chatRepository.save(chatMessage);
        return new ChatResponseDto(chatResponse);
    }

    public ChatMessageListResponseDto getChatList(Long userId, Long roomId) {
        chatRepository.updateStateByRoomIdAndUserId(roomId, userId);

        // 조훈창 수정 -> Stream Error 발생으로 collect 추가
        List<ChatMessageDto> chatMessageDtoList = (List<ChatMessageDto>) chatRepository.findAllByChatRoomId(roomId)
                .stream().map(ChatMessageDto::new).collect(Collectors.toList());
        User partner = userRepository.findPartnerByRoomIdAndUserId(roomId, userId);
        return new ChatMessageListResponseDto(partner, chatMessageDtoList);
    }

    public boolean stateUpdate(String chatId) {
        chatRepository.updateState(chatId);
        System.out.println("?");
        return true;
    }

    public ChatRoom getChatRoom(Long senderId , Long recipientId){
        return chatRoomRepository.findByOwnerAndInvited(senderId, recipientId);
    }


    public boolean existChatMessage(Long userId) {
        return chatRepository.findByChatMessage(userId) != null;
    }
}
