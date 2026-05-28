package com.event.resource;

import com.event.entity.UserEventRSVP;
import com.event.service.UserEventRSVPService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/rsvp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEventRSVPResource {

    @Inject
    UserEventRSVPService rsvpService;

    // POST /rsvp/user/1/event/1/status/GOING
    @POST
    @Path("/user/{userId}/event/{eventId}/status/{status}")
    public Response save(
        @PathParam("userId") Long userId,
        @PathParam("eventId") Long eventId,
        @PathParam("status") String status) {
        return Response.ok(rsvpService.save(userId, eventId, status))
                       .status(201).build();
    }

    // GET /rsvp/event/1
    @GET
    @Path("/event/{eventId}")
    public List<UserEventRSVP> byEvent(@PathParam("eventId") Long eventId) {
        return rsvpService.findByEvent(eventId);
    }

    // GET /rsvp/user/1
    @GET
    @Path("/user/{userId}")
    public List<UserEventRSVP> byUser(@PathParam("userId") Long userId) {
        return rsvpService.findByUser(userId);
    }
}