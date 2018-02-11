package com.jeseromero.resources;

import com.google.gson.Gson;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.Profile;
import com.jeseromero.model.Token;
import com.jeseromero.model.lightweight.JSONError;
import com.jeseromero.model.lightweight.LightMirrors;
import com.jeseromero.model.lightweight.SResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("{token}")
public class UserResources {

    private UserController userController = UserController.instance();

    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("token") String token) {
        Profile profile = userController.getProfile(new Token(token));

        if (profile != null) {
            return Response.ok()
                    .entity(new SResponse("ok", profile).toJSON())
                    .build();
        }

        return Response.ok(new SResponse("error", new JSONError(1, "Error retrieving profile, user do not exist")).toJSON()).build();
    }

    @GET
    @Path("retrieveMirrors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMirrors(@PathParam("token") String token) {
        List<String> mirrors = userController.getMirrors(new Token(token));

        if (mirrors != null) {
            return Response.ok()
                    .entity(new SResponse("ok", new LightMirrors(mirrors)).toJSON())
                    .build();
        }

        return Response.ok(new SResponse("error", new JSONError(3, "Error retrieving the mirrors")).toJSON()).build();
    }
}
