package com.pghub.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String phoneNo;

    @Column(name = "pg_id", nullable = false)
    private Integer pgId;

    @Lob
    @Column(name = "user_image")
    private String userImage;

    @Column(nullable = false)
    private Character gender;

    @Column(name = "room_no")
    private Integer roomNo;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String phoneNo, Character gender, Integer roomNo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.roomNo = roomNo;
        this.pgId = 1;  // change this according to logic
    }
}
