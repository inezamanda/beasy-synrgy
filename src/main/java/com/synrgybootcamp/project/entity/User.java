package com.synrgybootcamp.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "password")
    private String password;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "profil_picture")
    private String profilePicture;

    @Column(name = "pin", length = 6)
    private Integer pin;

    @Column(name = "verif_code")
    private String verifCode;

    @Column(name = "verif_code_status")
    private Boolean verifCodeStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_mission_completion_time")
    private Date date = new Date();

    @ManyToOne
    @JoinColumn(name = "current_planet_id", referencedColumnName = "id")
    private Planet planet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "ID"))
    private Set<Role> roles;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Contact> contacts;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Pocket> pockets;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<PocketTransaction> pocketTransactions;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Transaction> transactions;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Transfer> transfers;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserReward> userRewards;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserMission> userMissions;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<CreditCardBill> creditCardBills;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email) && accountNumber.equals(user.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, accountNumber);
    }
}
