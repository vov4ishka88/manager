package com.challenge.manager.model;

import jakarta.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer experienceMonths;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
