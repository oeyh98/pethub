package ium.pethub.domain.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false)
    private Owner owner;
}
