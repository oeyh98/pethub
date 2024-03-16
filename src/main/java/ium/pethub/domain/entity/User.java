package ium.pethub.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private String name;

    private String nickname;

    private String userImage;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String callNumber;

    @Column(length = 1000)
    private String refreshToken;

    private LocalDateTime withdrawAt;


    @ColumnDefault("0")
    private int withdrawYn;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Owner owner;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Vet vet;

    @Builder
    public User(RoleType role, String name, String nickname, String email, String password, String callNumber, String userImage) {
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.email = email;
        this.password = password;
        this.callNumber = callNumber;
        this.userImage = userImage;
    }


    public void joinOwner(Owner owner){
        this.owner = owner;
    }

    public void joinVet(Vet vet){
        this.vet = vet;
    }


    public void destroyRefreshToken(){
        this.refreshToken = null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void resetPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void updateNickname(String nickname){this.nickname = nickname;}

    public void updateUserImage(String userImage){this.userImage = userImage;}
    public void withdraw() {
        this.withdrawYn = 1;
        this.withdrawAt = LocalDateTime.now();
    }


}
