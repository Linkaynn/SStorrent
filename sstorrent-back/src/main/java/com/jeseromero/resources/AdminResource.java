package com.jeseromero.resources;

import com.jeseromero.controller.MirrorController;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.Log;
import com.jeseromero.model.Request;
import com.jeseromero.model.User;
import com.jeseromero.model.lightweight.JSONLightError;
import com.jeseromero.model.lightweight.JSONLightLogs;
import com.jeseromero.model.lightweight.JSONLightRequest;
import com.jeseromero.model.lightweight.JSONLightRequests;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.resources.responses.SResponse;
import com.jeseromero.util.SLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("admin/{token}")
public class AdminResource extends SResource{

    private static final SLogger logger = new SLogger(AdminResource.class.getName());

	private MirrorController mirrorController = MirrorController.instance();

    @GET
    @Path("getLogs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs(@PathParam("token") String token) {

	    User user = getUser(token);

	    if(user != null && user.isAdmin()){

		    Session session = DBSessionFactory.getSession();

		    List<Log> list = session.createQuery("from Log").list();

		    return Response.ok(new SResponse("ok", new JSONLightLogs(list)).toJSON()).build();
	    }

	    return Response.status(403).build();
    }

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

}
