package com.pghub.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cook")
@NoArgsConstructor
@Data
public class Cook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cookId;

    @Column(nullable = false)
    private Integer pgId;

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String cookPhoneNo;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin; // Many cooks belong to one admin.

    @Column(name="role")
    private String role;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
