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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public LocalDateTime eventDate;
    public boolean isFree;
    public LocalDateTime createdAt;

    @ManyToOne
    public Location location;

    @OneToOne(mappedBy = "event")
    public EventDetail detail;

    @OneToMany(mappedBy = "event")
    public List<Ticket> tickets;

    @ManyToMany
    public List<Tag> tags;

    @ManyToMany
    public List<User> attendees;
}