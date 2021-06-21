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
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "is_claimed")
    private Boolean claimed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_time")
    private Date date = new Date();

    @Column(name = "total_used")
    private Integer totalUsed;

    @Column(name = "is_expired")
    private Boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
