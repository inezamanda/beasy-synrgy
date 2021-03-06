package com.synrgybootcamp.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_rewards")
public class UserReward {

    @Id
    @Column(name = "id", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "is_claimed")
    private Boolean claimed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_time")
    private Date expiredAt;

    @Column(name = "total_used")
    private Integer totalUsed;

    @Column(name = "is_expired")
    private Boolean expired;

    @Column(name = "additional_information")
    private String additionalInformation;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    private RewardPlanet rewardPlanet;

    @Column(name = "reward_id", insertable = false, updatable = false)
    private String rewardId;
}
