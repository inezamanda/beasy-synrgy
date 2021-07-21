package com.synrgybootcamp.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pockets")
public class Pocket {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    @Column(name = "target")
    private Integer target;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "is_primary")
    private Boolean primary;

    @Column(name = "is_deleted")
    private Boolean delete;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    @OneToMany
    @JoinColumn(name = "pocket_id")
    private List<PocketTransaction> pocketTransactions;


}
