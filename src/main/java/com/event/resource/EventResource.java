package com.event.resource;

import com.event.entity.Event;
import com.event.entity.Tag;
import com.event.entity.Ticket;
import com.event.service.EventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    @Inject
    EventService eventService;

    @GET
    public List<Event> getAll() {
        return eventService.findAll();
    }
    @GET
    @Path("/upcoming")
    public List<Event> upcoming() {
        return eventService.findUpcoming();
    }
    @GET
    @Path("/search")
    public List<Event> search(@QueryParam("name") String name) {
        return eventService.findByName(name);
    }
    @GET
    @Path("/city/{city}")
    public List<Event> byCity(@PathParam("city") String city) {
        return eventService.findByCity(city);
    }
    @GET
    @Path("/{id}")
    public Event getById(@PathParam("id") Long id) {
        return eventService.findById(id);
    }
    @GET
    @Path("/{id}/tickets")
    public List<Ticket> getTickets(@PathParam("id") Long id) {
        Event event = eventService.findById(id);
        return event != null ? event.tickets : List.of();
    }
    @GET
    @Path("/{id}/tags")
    public List<Tag> getTags(@PathParam("id") Long id) {
        Event event = eventService.findById(id);
        return event != null ? event.tags : List.of();
    }

    @POST
    public Response create(Event event) {
        return Response.ok(eventService.create(event)).status(201).build();
    }
}