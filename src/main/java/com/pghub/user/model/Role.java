package com.pghub.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name ="role_name", unique = true)
    private RoleType role;

    @Column(name = "description")
    private String description;
}