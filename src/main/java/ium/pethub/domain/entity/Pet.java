package ium.pethub.domain.entity;

import ium.pethub.dto.pet.request.PetRequestDto;
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

    private String gender;

    private String age;

    private int weight;

    private String breed;

    private String introduction;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Builder
    public Pet(String name, String gender, String age, int weight, String breed, String introduction, String image, User owner) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
        this.introduction = introduction;
        this.image = image;
        this.user = owner;
    }

    public void update(PetRequestDto requestDto){
        this.name = requestDto.getPetName();
        this.gender = requestDto.getPetGender();
        this.age = requestDto.getPetAge();
        this.weight = requestDto.getPetWeight();
        this.breed = requestDto.getPetBreed();
        this.introduction = requestDto.getPetIntroduction();
        this.image = requestDto.getPetImage();
    }

    public void setImage(String petImage) {
        this.image = petImage;
    }
}
