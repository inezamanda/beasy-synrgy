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

    @Column(name = "story_telling", columnDefinition = "TEXT")
    private String story_telling;

    @Column(name = "sequence")
    private Integer sequence;

    @OneToOne(mappedBy = "planet")
    private RewardPlanet rewardPlanet;

    @OneToMany
    @JoinColumn(name = "planet_id")
    private List<Mission> missions;

    @OneToMany
    @JoinColumn(name = "current_planet_id")
    private List<User> users;
}
