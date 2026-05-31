package com.event.service;

import com.event.entity.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import com.event.entity.Ticket;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EventService {

    @Inject
    EntityManager em;

    @Transactional
    public Event create(Event event) {
    	 if (event.tickets != null) {
    	        for (Ticket ticket : event.tickets) {
    	            ticket.event = event;
    	        }
    	    }
    	    em.persist(event);
    	    return event;
    }

    public List<Event> findAll() {
        return em.createQuery("SELECT e FROM Event e", Event.class)
                 .getResultList();
    }
    public List<Event> findByCity(String city) {
        return em.createQuery(
            "SELECT e FROM Event e WHERE e.location.city = :city",
            Event.class)
            .setParameter("city", city)
            .getResultList();
    }
    public List<Event> findByName(String name) {
        return em.createQuery(
            "SELECT e FROM Event e WHERE LOWER(e.name) LIKE LOWER(:name)",
            Event.class)
            .setParameter("name", "%" + name + "%")
            .getResultList();
    }
    public List<Event> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return em.createQuery(
            "SELECT e FROM Event e WHERE e.eventDate BETWEEN :from AND :to",
            Event.class)
            .setParameter("from", from)
            .setParameter("to", to)
            .getResultList();
    }
    public List<Event> findUpcoming() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoWeeks = now.plusDays(30);
        return em.createQuery(
            "SELECT e FROM Event e WHERE e.eventDate BETWEEN :now AND :twoWeeks",
            Event.class)
            .setParameter("now", now)
            .setParameter("twoWeeks", twoWeeks)
            .getResultList();
    }
    public Event findById(Long id) {
        return em.find(Event.class, id);
    }
}