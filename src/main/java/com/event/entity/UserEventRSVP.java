package com.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_event_rsvp")
public class UserEventRSVP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public User user;

    @ManyToOne
    public Event event;

    // Possible values:
    // INTERESTED, GOING, TRANSFER_NEEDED,
    // LOOKING_FOR_PLACE, OFFERING_TRANSFER
    public String status;

    public LocalDateTime respondedAt;
}