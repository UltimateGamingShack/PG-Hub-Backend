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
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String userPhoneNo;

    @Column(name = "pg_id", nullable = false)
    private Integer pgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin; // Many users belong to one admin.

    @Lob
    @Column(name = "user_image")
    private String userImage;

    @Column(nullable = false)
    private Character gender;

    @Column(name = "room_no")
    private Integer roomNo;

    @Column(name="role")
    private String role;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
