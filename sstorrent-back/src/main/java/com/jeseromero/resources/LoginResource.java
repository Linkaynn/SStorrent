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
import java.util.List;

@Path("login")
public class LoginResource {

    private static final SLogger logger = new SLogger(LoginResource.class.getName());

    private UserController userController = UserController.instance();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {

        JSONLightUser jsonLightUser = userController.login(username, password);

	    if (jsonLightUser == null) {
	    	return Response.ok(new SResponse("error", new JSONError(0, "Username or password is wrong")).toJSON()).build();
	    }

	    Session session = DBSessionFactory.openSession();

	    try {
		    List<Request> requests = session.createQuery("from Request where username = '" + jsonLightUser.getUsername() + "' ").list();

		    Transaction transaction = session.beginTransaction();
		    for (Request request : requests) {
			    session.delete(request);
		    }
		    transaction.commit();
	    } catch (Exception ignored) {
	    	ignored.printStackTrace();
	    }

	    logger.info("User logged: " + jsonLightUser.getName());

	    return Response.ok().entity(new SResponse("ok", jsonLightUser).toJSON()).build();

    }
}
