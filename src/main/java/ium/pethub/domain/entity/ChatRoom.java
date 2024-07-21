package ium.pethub.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id" , updatable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_id",  updatable = false)
    private User invited; // 비식별관계로 왜래키를 둘 다 물고있는게 맞나?

    // 회원이 탈퇴하면 채팅방은 그냥 사라지는가?

    @Builder
    public ChatRoom(User owner, User invited) {
        this.owner = owner;
        this.invited = invited;
    }
}
