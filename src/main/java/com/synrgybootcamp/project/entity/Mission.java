package com.synrgybootcamp.project.entity;

import com.synrgybootcamp.project.enums.MissionType;
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
@Table(name = "missions")
public class Mission {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_type")
    private MissionType missionType;

    @Column(name = "Target")
    private Integer target;

    @Column(name = "wording")
    private String wording;

    @ManyToOne
    @JoinColumn(name = "planet_id", referencedColumnName = "id")
    private Planet planet;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserMission> userMissions;
}
