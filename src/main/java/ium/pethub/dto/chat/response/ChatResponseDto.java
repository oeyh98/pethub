package ium.pethub.dto.chat.response;

import ium.pethub.domain.entity.Chat;
import ium.pethub.domain.entity.MessageEnum;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ChatResponseDto {

    private String chatId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private Long roomId;
    private LocalDateTime createdAt;
    private Boolean state;
    private MessageEnum type;


    public ChatResponseDto(Chat chat) {
        this.chatId = chat.getId();
        this.senderId = chat.getSenderId();
        this.recipientId = chat.getRecipientId();
        this.content = chat.getContent();
        this.roomId = chat.getChatRoom().getId();
        this.createdAt = chat.getCreatedAt();
        this.state = chat.isState();
        this.type = MessageEnum.MESSAGE;
    }

}
