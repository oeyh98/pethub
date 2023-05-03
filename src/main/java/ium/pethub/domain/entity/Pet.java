package ium.pethub.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Pet extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "pet_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User owner;

    @Builder
    public Pet(Long id, String name, String gender, String age, char weight, String breed, String introduction, User owner) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
        this.introduction = introduction;
        this.owner = owner;
    }
}
