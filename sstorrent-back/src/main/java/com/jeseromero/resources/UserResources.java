package com.jeseromero.resources;

import com.jeseromero.controller.UserController;
import com.jeseromero.model.Mirror;
import com.jeseromero.model.Profile;
import com.jeseromero.model.Token;
import com.jeseromero.model.lightweight.JSONError;
import com.jeseromero.model.lightweight.LightMirrors;
import com.jeseromero.model.lightweight.SResponse;
import com.jeseromero.persistence.DBSessionFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


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
        List<String> mirrors = userController.getMirrors(new Token(token)).stream().map(Mirror::getName).collect(Collectors.toList());

        if (mirrors != null) {
            return Response.ok()
                    .entity(new SResponse("ok", new LightMirrors(mirrors)).toJSON())
                    .build();
        }

        return Response.ok(new SResponse("error", new JSONError(3, "Error retrieving the mirrors")).toJSON()).build();
    }


    @GET
    @Path("retrieveAllMirrors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMirrors(@PathParam("token") String token) {
	    List<Mirror> allMirrors = DBSessionFactory.instance().openSession().createQuery("from Mirror").list();

	    List<String> mirrors = allMirrors.stream().map(Mirror::getName).collect(Collectors.toList());

        if (mirrors != null) {
            return Response.ok()
                    .entity(new SResponse("ok", new LightMirrors(mirrors)).toJSON())
                    .build();
        }

        return Response.ok(new SResponse("error", new JSONError(3, "Error retrieving the mirrors")).toJSON()).build();
    }

	@POST
	@Path("updateProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam("token") String token,
	                              @FormParam("name") String name,
	                              @FormParam("mirrors") String mirrorsByComma) {
		String[] mirrors = mirrorsByComma.split(",");

		try {
			userController.updateProfile(new Token(token), name, mirrors);
		} catch (Exception e) {
			e.printStackTrace();

			return Response.ok(new SResponse("error", new JSONError(4, "Error updating the profile")).toJSON()).build();
		}

		return Response.ok(new SResponse("ok").toJSON()).build();

	}
}
