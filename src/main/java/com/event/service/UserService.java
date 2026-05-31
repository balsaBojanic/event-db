package com.event.service;

import com.event.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import com.event.service.TimezoneService;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    @Transactional
    public User create(User user) {
        em.persist(user);
        return user;
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                 .getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }
    
    
}