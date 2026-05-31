package com.event.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "event_detail")
public class EventDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String description;
    public String imageUrl;
    public Integer capacity;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "event_id")
    public Event event;
}