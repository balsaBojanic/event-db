package com.event.resource;

import com.event.entity.User;
import com.event.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public List<User> getAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    public User getById(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @POST
    @RolesAllowed("admin")
    public Response create(User user) {
        return Response.ok(userService.create(user)).status(201).build();
    }
}