package com.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String bio;
    public String profilePictureUrl;
    public String city;
    public String country;
    public String phoneNumber;

    // This side owns the relationship - holds the foreign key
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}