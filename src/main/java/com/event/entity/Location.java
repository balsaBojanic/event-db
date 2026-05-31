package com.event.entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String address;
    public String city;
    public String country;
    public Double latitude;
    public Double longitude;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    public List<Event> events;
}