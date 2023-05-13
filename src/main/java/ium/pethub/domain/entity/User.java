package ium.pethub.domain.entity;

import ium.pethub.dto.user.request.UserUpdateRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private LocalDate birth;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String address;

    private String userImage;

    @Column(length = 1000)
    private String refreshToken;

    @ColumnDefault("0")
    private int withdrawYn;

    private LocalDateTime withdrawAt;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Post> postList = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Comment> petList = new ArrayList<>();


    @Builder
    public User(String email, String password, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.role = RoleType.USER;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public void destroyRefreshToken(){
        this.refreshToken = null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void update(UserUpdateRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.userImage = requestDto.getUserImage();
    }

    public void resetPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void withdraw() {
        this.withdrawYn = 1;
        this.withdrawAt = LocalDateTime.now();
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
