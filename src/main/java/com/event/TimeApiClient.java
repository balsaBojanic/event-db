package com.event;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://timeapi.io")
public interface TimeApiClient {

    @GET
    @Path("/api/time/current/ip")
    @Produces(MediaType.APPLICATION_JSON)
    TimeZoneResponse getTimeByIp(@QueryParam("ipAddress") String ipAddress);
}