package ium.pethub.dto.chat.response;

import ium.pethub.domain.entity.MessageEnum;
import lombok.Getter;


@Getter
public class SocketStatus {
    private int status;

    private String message;

    private MessageEnum type;
    public SocketStatus(int status, String error,MessageEnum type) {
        this.status = status;
        this.message = error;
        this.type = type;
    }
}
