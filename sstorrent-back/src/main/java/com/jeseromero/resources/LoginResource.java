package com.jeseromero.resources;

import com.jeseromero.controller.SessionController;
import com.jeseromero.model.lightweight.JSONLightError;
import com.jeseromero.model.lightweight.JSONLightUser;
import com.jeseromero.resources.responses.SResponse;
import com.jeseromero.util.SLogger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginResource {

    private static final SLogger logger = new SLogger(LoginResource.class.getName());

    private SessionController sessionController = SessionController.instance();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {

        JSONLightUser jsonLightUser = sessionController.login(username, password);

	    if (jsonLightUser == null) {
		    logger.info(username, "Try to login");
	    	return Response.ok(new SResponse("error", new JSONLightError(0, "Username or password is wrong")).toJSON()).build();
	    }

	    logger.info(jsonLightUser.getUsername(), "Log in");

	    return Response.ok().entity(new SResponse("ok", jsonLightUser).toJSON()).build();

    }
}
