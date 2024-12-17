package com.pghub.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
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