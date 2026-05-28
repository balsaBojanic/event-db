package com.event.service;

import com.event.entity.Event;
import com.event.entity.User;
import com.event.entity.UserEventRSVP;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UserEventRSVPService {

    @Inject
    EntityManager em;

    @Transactional
    public UserEventRSVP save(Long userId, Long eventId, String status) {
        UserEventRSVP rsvp = new UserEventRSVP();
        rsvp.user = em.find(User.class, userId);
        rsvp.event = em.find(Event.class, eventId);
        rsvp.status = status;
        rsvp.respondedAt = LocalDateTime.now();
        em.persist(rsvp);
        return rsvp;
    }

    public List<UserEventRSVP> findByEvent(Long eventId) {
        return em.createQuery(
            "SELECT r FROM UserEventRSVP r WHERE r.event.id = :eventId",
            UserEventRSVP.class)
            .setParameter("eventId", eventId)
            .getResultList();
    }

    public List<UserEventRSVP> findByUser(Long userId) {
        return em.createQuery(
            "SELECT r FROM UserEventRSVP r WHERE r.user.id = :userId",
            UserEventRSVP.class)
            .setParameter("userId", userId)
            .getResultList();
    }
}