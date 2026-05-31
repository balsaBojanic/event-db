package com.event.service;

import com.event.IpifyClient;
import com.event.TimeApiClient;
import com.event.TimeZoneResponse;
import com.event.entity.TimeZoneInfo;
import com.event.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TimezoneService {

    @Inject
    EntityManager em;

    @RestClient
    IpifyClient ipifyClient;

    @RestClient
    TimeApiClient timeApiClient;

    @Transactional
    public User assignTimezoneToUser(Long userId) {
        
        User user = em.find(User.class, userId);

        
        if (user == null) {
            throw new WebApplicationException(
                "User with id " + userId + " not found",
                Response.Status.NOT_FOUND
            );
        }

        
        String ip = ipifyClient.getPublicIp();

        
        TimeZoneResponse tzResponse = timeApiClient.getTimeByIp(ip);

        
        TimeZoneInfo tzInfo = new TimeZoneInfo();
        tzInfo.ipAddress = tzResponse.ipAddress;
        tzInfo.timeZone = tzResponse.timeZone;
        tzInfo.currentLocalTime = tzResponse.currentLocalTime;
        tzInfo.countryName = tzResponse.countryName;
        tzInfo.cityName = tzResponse.cityName;
        tzInfo.user = user;

        
        em.persist(tzInfo);

        if (user.timeZones == null) {
            user.timeZones = new java.util.ArrayList<>();
        }
        user.timeZones.add(tzInfo);

        return user;
    }
}