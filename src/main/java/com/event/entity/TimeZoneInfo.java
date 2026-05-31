package com.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "timezone_info")
public class TimeZoneInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String ipAddress;
    public String timeZone;
    public String currentLocalTime;
    public String countryName;
    public String cityName;

    @JsonIgnore
    @ManyToOne
    public User user;
}