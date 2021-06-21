package com.synrgybootcamp.project.entity;

import com.synrgybootcamp.project.enums.RewardPlanetName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rewardplanet")
public class RewardPlanet {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RewardPlanetName rewardName;

    @Column(name = "wording")
    private String wording;

    @Column(name = "tnc", columnDefinition = "TEXT")
    private String tnc;

    @Column(name = "amount")
    private Integer amount;

    @OneToOne
    @JoinColumn(name = "planet_id", referencedColumnName = "id")
    private Planet planet;
}
