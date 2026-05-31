package com.event;

import com.event.entity.Event;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EventScheduler {

    @Inject
    EntityManager em;

    
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void logPastEvents() {
        LocalDateTime now = LocalDateTime.now();

        List<Event> pastEvents = em.createQuery(
            "SELECT e FROM Event e WHERE e.eventDate < :now",
            Event.class)
            .setParameter("now", now)
            .getResultList();

        System.out.println("=== SCHEDULER RAN AT " + now + " ===");
        System.out.println("Found " + pastEvents.size() + " past events:");

        for (Event e : pastEvents) {
            System.out.println("  - [ID: " + e.id + "] " + e.name + 
                             " was on " + e.eventDate);
        }

        System.out.println("=== END OF SCHEDULER REPORT ===");
    }

    
    @Scheduled(every = "5m")
    @Transactional
    public void checkUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24Hours = now.plusHours(24);

        List<Event> soonEvents = em.createQuery(
            "SELECT e FROM Event e WHERE e.eventDate BETWEEN :now AND :in24Hours",
            Event.class)
            .setParameter("now", now)
            .setParameter("in24Hours", in24Hours)
            .getResultList();

        if (!soonEvents.isEmpty()) {
            System.out.println("=== UPCOMING EVENTS IN NEXT 24H ===");
            for (Event e : soonEvents) {
                System.out.println("  - " + e.name + " starts at " + e.eventDate);
            }
        }
    }
}