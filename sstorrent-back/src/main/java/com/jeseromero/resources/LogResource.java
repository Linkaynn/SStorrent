package com.jeseromero.resources;

import com.jeseromero.controller.UserController;
import com.jeseromero.model.Log;
import com.jeseromero.model.User;
import com.jeseromero.model.lightweight.JSONLightLogs;
import com.jeseromero.persistence.DBSessionFactory;
import com.jeseromero.resources.responses.SResponse;
import com.jeseromero.util.SLogger;
import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("log/{token}")
public class LogResource extends SResource{

    private static final SLogger logger = new SLogger(LogResource.class.getName());

    private static final UserController userController = UserController.instance();

    @GET
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
}
