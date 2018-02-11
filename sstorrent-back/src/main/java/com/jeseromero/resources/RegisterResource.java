package com.jeseromero.resources;

import com.jeseromero.controller.UserController;
import com.jeseromero.model.Request;
import com.jeseromero.model.lightweight.JSONError;
import com.jeseromero.model.lightweight.JSONLightUser;
import com.jeseromero.model.lightweight.SResponse;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.util.SLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sendRequest")
public class RegisterResource {

    private static final SLogger logger = new SLogger(RegisterResource.class.getName());


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@FormParam("name") String name, @FormParam("email") String email, @FormParam("message") String message) {

    	if (name != null && email != null && message != null) {

		    Request request = new Request(name, email, message);

		    Session session = DBSessionFactory.instance().openSession();

		    try {
			    Transaction transaction = session.beginTransaction();
			    session.save(request);
			    transaction.commit();
			    return Response.ok(new SResponse("ok").toJSON()).build();
		    }catch (Exception ex) {
		    	ex.printStackTrace();
		    	return Response.ok(new SResponse("error", new JSONError(5, "Error making the request")).toJSON()).build();
		    }finally {

		    	session.close();
		    }

	    }
	    return Response.ok(new SResponse("error", new JSONError(6, "Some fields are null")).toJSON()).build();

    }
}
