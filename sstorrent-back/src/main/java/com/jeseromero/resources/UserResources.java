package com.jeseromero.resources;

import com.jeseromero.controller.SearchController;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.*;
import com.jeseromero.model.lightweight.*;
import com.jeseromero.persistence.DBSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("{token}")
public class UserResources {

    private UserController userController = UserController.instance();

	private SearchController searchController = SearchController.instance();

	@GET
	@Path("getRequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequests(@PathParam("token") String token) {

		if (userController.isLogged(new Token(token))) {
			Session session = DBSessionFactory.openSession();

			List<Request> requests;

			try {
				requests = session.createQuery("from Request").list();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok(new SResponse("error", new JSONError(8, "Error getting the requests")).toJSON()).build();
			}

			return Response.ok()
					.entity(new SResponse("ok", new JSONLightRequests(requests)).toJSON())
					.build();

		}

		return Response.ok(new SResponse("error", new JSONError(7, "Error getting the requests")).toJSON()).build();
	}

	@POST
	@Path("rejectRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rejectRequest(@PathParam("token") String token, @FormParam("requestId") int requestId) {

		if (userController.isLogged(new Token(token))) {
			Session session = DBSessionFactory.openSession();

			Request request;

			try {
				request = session.get(Request.class, requestId);

				Transaction transaction = session.beginTransaction();
				session.delete(request);
				transaction.commit();

			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok(new SResponse("error", new JSONError(9, "Error rejecting the request")).toJSON()).build();
			}

			return Response.ok()
					.entity(new SResponse("ok", new JSONLightRequest(request)).toJSON())
					.build();

		}

		return Response.ok(new SResponse("error", new JSONError(10, "Error rejecting the request")).toJSON()).build();
	}

	@POST
	@Path("acceptRequest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptRequest(@PathParam("token") String token, @FormParam("requestId") int requestId) {

		if (userController.isLogged(new Token(token))) {
			Session session = DBSessionFactory.openSession();

			Request request;

			try {
				request = session.get(Request.class, requestId);

				User newUser = new User(request.getUsername(), request.getName(), request.getEmail(), request.getPassword());

				newUser.setMirrors(searchController.getAllMirrors());

				Transaction transaction = session.beginTransaction();
				session.save(newUser);
				transaction.commit();

			} catch (Exception e) {
				e.printStackTrace();
				return Response.ok(new SResponse("error", new JSONError(11, "Error accepting the request")).toJSON()).build();
			}

			return Response.ok()
					.entity(new SResponse("ok", new JSONLightRequest(request)).toJSON())
					.build();

		}

		return Response.ok(new SResponse("error", new JSONError(12, "Error accepting the request")).toJSON()).build();
	}

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
	    List<Mirror> allMirrors = DBSessionFactory.openSession().createQuery("from Mirror").list();

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
	                              @FormParam("newPassword") String newPassword,
	                              @FormParam("mirrors") String mirrorsByComma) {

		String[] mirrors = mirrorsByComma.split(",");

		try {
			Token _token = new Token(token);

			userController.updateProfile(_token, name, mirrors);

			if (!newPassword.equals("null")) {
				userController.changePassword(_token, newPassword);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return Response.ok(new SResponse("error", new JSONError(4, "Error updating the profile")).toJSON()).build();
		}

		return Response.ok(new SResponse("ok").toJSON()).build();

	}
}
