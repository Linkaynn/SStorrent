package com.jeseromero.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.lightweight.JSONLightUser;
import com.jeseromero.util.SLogger;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginResource {

    private static final SLogger logger = new SLogger(LoginResource.class.getName());

    @Inject
    private UserController userController;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String params) {
        JsonObject jsonObject = new JsonParser().parse(params).getAsJsonObject();

        try {
            JSONLightUser jsonLightUser = userController.login(jsonObject.get("username").getAsString(), jsonObject.get("password").getAsString());

            logger.info("User logged: " + jsonLightUser.getName());

            return Response.ok()
                    .entity(new Gson().toJson(jsonLightUser))
                    .build();

        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());

            return Response.status(403).build();
        }

    }
}
