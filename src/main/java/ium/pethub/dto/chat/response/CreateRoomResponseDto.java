package ium.pethub.dto.chat.response;

import ium.pethub.domain.entity.ChatRoom;
import lombok.Getter;

@Getter
public class CreateRoomResponseDto {

    private Long roomId;

    private String owner;

    private String invited;

    public CreateRoomResponseDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getId();
        this.owner = chatRoom.getOwner().getName();
        this.invited = chatRoom.getInvited().getName();
    }
}
