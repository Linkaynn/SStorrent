package com.jeseromero.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.Profile;
import com.jeseromero.model.lightweight.JSONLightUser;
import com.jeseromero.util.SLogger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("{token}")
public class UserResources {

    @Inject
    private UserController userController;

    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("token") String token) {
        Profile profile = userController.getProfile(token);

        if (profile != null) {
            return Response.ok()
                    .entity(new Gson().toJson(profile))
                    .build();
        }

        return Response.status(404).build();
    }

    @GET
    @Path("retrieveMirrors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMirrors(@PathParam("token") String token) {
        List<String> mirrors = userController.getMirrors(token);

        if (mirrors != null) {
            return Response.ok()
                    .entity(new Gson().toJson(mirrors))
                    .build();
        }

        return Response.status(404).build();
    }
}
