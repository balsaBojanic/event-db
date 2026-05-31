package com.event.entity;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String websiteUrl;
    public LocalDateTime eventDate;
    public boolean isFree;
    public LocalDateTime createdAt;

    @ManyToOne
    public Location location;

    @OneToOne(mappedBy = "event", fetch = FetchType.LAZY)
    public EventDetail detail;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Ticket> tickets;

    @ManyToMany(fetch = FetchType.LAZY)
    public List<Tag> tags;

    
   
}