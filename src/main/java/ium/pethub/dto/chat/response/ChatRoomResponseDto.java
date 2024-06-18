package ium.pethub.dto.chat.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private Long partnerId;
    // 조훈창 - 수정 nickName-> name으로 수정   
    // private String nickName;
    private String name;
    private String userImage;
    private Long unReadMsgCnt;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

}
