package com.pghub.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_name", nullable = false, unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String phoneNo;

    @Column(name = "pg_id", nullable = false)
    private Integer pgId;

//    @Lob - old bug for postgres -https://stackoverflow.com/questions/60381895/error-column-image-is-of-type-bytea-but-expression-is-of-type-oid-in-postgres
   // NEVER USE Byte, use byte, :( it took me 3 hours to resolve this issue
//    @Column(name = "user_image")
//    private byte[] userImage;
@Column(name = "user_image")
private String userImage;

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    private Character gender;

    @Column(name = "room_no")
    private Integer roomNo;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at",nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String phoneNo, Character gender, Integer roomNo,Integer pgId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.roomNo = roomNo;
        this.pgId = pgId;  // change this according to logic
    }

//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = Instant.now();
//    }
}
