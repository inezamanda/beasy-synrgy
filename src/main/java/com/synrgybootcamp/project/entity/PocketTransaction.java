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
@Table(name = "pocket_transactions")
public class PocketTransaction {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "amount")
    private Integer amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "source_pocket_id", referencedColumnName = "id")
    private Pocket sourcePocket;

    @ManyToOne
    @JoinColumn(name = "destination_pocket_id", referencedColumnName = "id")
    private Pocket destinationPocket;

}
