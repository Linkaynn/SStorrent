package com.jeseromero.resources;

import com.jeseromero.controller.MirrorController;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.Mirror;
import com.jeseromero.model.Profile;
import com.jeseromero.model.Request;
import com.jeseromero.model.User;
import com.jeseromero.model.lightweight.JSONLightError;
import com.jeseromero.model.lightweight.JSONLightMirrors;
import com.jeseromero.model.lightweight.JSONLightRequest;
import com.jeseromero.model.lightweight.JSONLightRequests;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.resources.responses.SResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("{token}")
public class UserResources extends SResource {

    private UserController userController = UserController.instance();

    private MirrorController mirrorController = MirrorController.instance();

	@GET
	@Path("getRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequests(@PathParam("token") String token) {
		User user = getUser(token);

		if (user != null) {
			Session session = DBSessionFactory.getSession();

			List<Request> requests;

			try {
				requests = session.createQuery("from Request").list();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok(new SResponse("error", new JSONLightError(8, "Error getting the requests")).toJSON()).build();
			}

			return Response.ok()
					.entity(new SResponse("ok", new JSONLightRequests(requests)).toJSON())
					.build();

		}

		return Response.ok(new SResponse("error", new JSONLightError(7, "Error getting the requests")).toJSON()).build();
	}

	@POST
	@Path("rejectRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rejectRequest(@PathParam("token") String token, @FormParam("requestId") int requestId) {
		User user = getUser(token);

		if (user != null) {
			Session session = DBSessionFactory.getSession();

			Request request;

			try {
				request = session.get(Request.class, requestId);

				Transaction transaction = session.beginTransaction();
				session.delete(request);
				transaction.commit();

			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok(new SResponse("error", new JSONLightError(9, "Error rejecting the request")).toJSON()).build();
			}

			return Response.ok()
					.entity(new SResponse("ok", new JSONLightRequest(request)).toJSON())
					.build();

		}

		return Response.ok(new SResponse("error", new JSONLightError(10, "Error rejecting the request")).toJSON()).build();
	}

	@POST
	@Path("acceptRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptRequest(@PathParam("token") String token, @FormParam("requestId") int requestId) {
		User user = getUser(token);

		if (user != null) {
			Session session = DBSessionFactory.getSession();

			Request request;

			try {
				request = session.get(Request.class, requestId);

				User newUser = new User(request.getUsername(), request.getName(), request.getEmail(), request.getPassword());

				newUser.setMirrors(mirrorController.getAllWorkingMirrors());

				Transaction transaction = session.beginTransaction();
				session.save(newUser);
				transaction.commit();

			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok(new SResponse("error", new JSONLightError(11, "Error accepting the request")).toJSON()).build();
			}

			return Response.ok()
					.entity(new SResponse("ok", new JSONLightRequest(request)).toJSON())
					.build();

		}

		return Response.ok(new SResponse("error", new JSONLightError(12, "Error accepting the request")).toJSON()).build();
	}

    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("token") String token) {
	    User user = getUser(token);

	    if (user != null) {
		    Profile profile = userController.getProfile(user);

		    if (profile != null) {
			    return Response.ok()
					    .entity(new SResponse("ok", profile).toJSON())
					    .build();
		    }
	    }

        return Response.ok(new SResponse("error", new JSONLightError(1, "Error retrieving profile, user do not exist")).toJSON()).build();
    }

    @GET
    @Path("retrieveMirrors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMirrors(@PathParam("token") String token) {
	    User user = getUser(token);

	    if (user != null) {
		    List<String> mirrors = user.getMirrors().stream().map(Mirror::getName).collect(Collectors.toList());

		    if (mirrors != null) {
			    return Response.ok(new SResponse("ok", new JSONLightMirrors(mirrors)).toJSON()).build();
		    }
	    }

        return Response.ok(new SResponse("error", new JSONLightError(3, "Error retrieving the mirrors")).toJSON()).build();
    }


    @GET
    @Path("retrieveAllMirrors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMirrors(@PathParam("token") String token) {
	    List<String> mirrors = mirrorController.getAllWorkingMirrorsName();

        if (mirrors != null) {
            return Response.ok()
                    .entity(new SResponse("ok", new JSONLightMirrors(mirrors)).toJSON())
                    .build();
        }

        return Response.ok(new SResponse("error", new JSONLightError(3, "Error retrieving the mirrors")).toJSON()).build();
    }

	@POST
	@Path("updateProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam("token") String token,
	                              @FormParam("name") String name,
	                              @FormParam("newPassword") String newPassword,
	                              @FormParam("mirrors") String mirrorsByComma) {

		String[] mirrors = mirrorsByComma.split(",");

		try {
			User user = getUser(token);

			if (user != null) {
				userController.updateProfile(user, name, mirrors);

				if (!newPassword.isEmpty() && !newPassword.equals("null")) {
					userController.changePassword(user, newPassword);
				}
			} else {
				// TODO Respond error
				return Response.ok(new SResponse("error", new JSONLightError(4, "Error updating the profile")).toJSON()).build();
			}

		} catch (Exception e) {
			logger.error(e);

			return Response.ok(new SResponse("error", new JSONLightError(4, "Error updating the profile")).toJSON()).build();
		}

		return Response.ok(new SResponse("ok").toJSON()).build();

	}
}
