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

    // 조훈창 수정 : name 추가
    private String name;
    
    public ChatMessageListResponseDto(User user, List<ChatMessageDto> chatMessageDtoList) {
        this.partnerId = user.getId();
        this.chatMessageList = chatMessageDtoList;
        // 조훈창 수정 : name 추가, nickname으로 변경, userImage 추가
        this.nickName = user.getNickname();
        this.name = user.getName();

        this.userImage = user.getUserImage();
    }
}
