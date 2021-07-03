package com.synrgybootcamp.project.entity;

import com.synrgybootcamp.project.enums.PocketTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
    @CreationTimestamp
    @Column(name = "date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private PocketTransactionType pocketTransactionType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "source_pocket_id", referencedColumnName = "id")
    private Pocket sourcePocket;

    @Column(name = "source_pocket_id", insertable = false, updatable = false)
    private String sourcePocketId;

    @ManyToOne
    @JoinColumn(name = "destination_pocket_id", referencedColumnName = "id")
    private Pocket destinationPocket;

    @Column(name = "destination_pocket_id", insertable = false, updatable = false)
    private String destinationPocketId;

}
