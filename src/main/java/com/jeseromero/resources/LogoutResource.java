package com.jeseromero.resources;

import com.jeseromero.controller.UserController;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("logout")
public class LogoutResource {

    @Inject
    private UserController userController;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@QueryParam("token") String token) {
        if (userController.logout(token)) {
            return Response.ok().build();
        }

        return Response.status(404).build();
    }
}
