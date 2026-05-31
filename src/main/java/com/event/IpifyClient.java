package com.event;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.ipify.org")
public interface IpifyClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getPublicIp();
}