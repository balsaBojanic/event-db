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
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import com.event.service.FileUploadService;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import java.io.InputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
    @RolesAllowed("admin")
    public Response create(Event event) {
        return Response.ok(eventService.create(event)).status(201).build();
        
    }
    @Inject
    FileUploadService fileUploadService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @QueryParam("eventId") Long eventId,
            MultipartFormDataInput input) {
        try {
            Map<String, List<InputPart>> formParts = input.getFormDataMap();

            String fileName = formParts.get("filename")
                    .get(0).getBodyAsString();

            InputStream fileStream = formParts.get("file")
                    .get(0).getBody(InputStream.class, null);

            return fileUploadService.uploadFile(eventId, fileName, fileStream);

        } catch (Exception e) {
            return Response.serverError()
                    .entity("Upload failed: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/files")
    public Response getEventWithFiles(@PathParam("id") Long id) {
        return Response.ok(fileUploadService.getEventWithFiles(id)).build();
    }
}