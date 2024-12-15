package com.pghub.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "admin")
@NoArgsConstructor
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;

    private String adminName;

    @Column(unique = true, nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String adminPassword;

    @Column(unique = true, nullable = false)
    private String adminPhoneNo;

    @Column(nullable = false)
    private Integer pgId;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users; // One admin can manage many users.

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Cook> cooks; // One admin can manage many cooks.


    @Column(name="role")
    private String role;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
