package com.synrgybootcamp.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "storytelling", columnDefinition = "TEXT")
    private String storytelling;

    @Column(name = "image")
    private String image;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "wording")
    private String wording;

    @OneToOne(mappedBy = "planet")
    private RewardPlanet rewardPlanet;

    @OneToMany
    @JoinColumn(name = "planet_id")
    private List<Mission> missions;

    @OneToMany
    @JoinColumn(name = "current_planet_id")
    private List<User> users;
}
