package com.event.service;

import com.event.entity.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class EventService {

    @Inject
    EntityManager em;

    @Transactional
    public Event create(Event event) {
        em.persist(event);
        return event;
    }

    public List<Event> findAll() {
        return em.createQuery("SELECT e FROM Event e", Event.class)
                 .getResultList();
    }
}