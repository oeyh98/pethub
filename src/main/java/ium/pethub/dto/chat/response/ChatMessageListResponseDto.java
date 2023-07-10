package ium.pethub.dto.chat.response;

import ium.pethub.domain.entity.User;
import ium.pethub.dto.chat.request.ChatMessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ChatMessageListResponseDto {

    private Long partnerId;
    private String nickName;
    private String userImage;
    private List<ChatMessageDto> chatMessageList;

    public ChatMessageListResponseDto(User user, List<ChatMessageDto> chatMessageDtoList) {
        this.partnerId = user.getId();
        this.nickName = user.getName();
        this.chatMessageList = chatMessageDtoList;
    }
}
