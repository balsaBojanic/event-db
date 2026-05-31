package com.event.entity;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;
    public String email;
    public String passwordHash;
    public String authProvider;
    public String externalId;
    public LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    public UserProfile profile;
 
}