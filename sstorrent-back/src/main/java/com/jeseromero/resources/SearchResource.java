package com.jeseromero.resources;

import com.jeseromero.controller.SearchController;
import com.jeseromero.controller.UserController;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.Token;
import com.jeseromero.model.lightweight.*;
import com.jeseromero.resources.responses.SResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("search")
public class SearchResource {

    private SearchController searchController = SearchController.instance();


    private UserController userController = UserController.instance();

    @GET
    @Path("{mirror}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("mirror") String mirror,
                           @PathParam("value") String value,
                           @QueryParam("token") String token) {

	    Token _token = new Token(token);

	    if (isLogged(_token)) {

	    	Collection<Torrent> torrents = searchController.search(mirror, value, 0);

		    JSONLightTorrents data = new JSONLightTorrents();

	        if (torrents != null && !torrents.isEmpty()) {
	            data = new JSONLightTorrents(torrents.stream().map(JSONLightTorrent::new).collect(Collectors.toList()));
	        }

	        try {
		        userController.registerSearch(_token, value);
	        } catch (IllegalStateException ignored) {}

	        return Response.ok(new SResponse("ok", data).toJSON()).build();
        }

        return Response.ok(new SResponse("error", new JSONLightError(2, "Error searching torrents of " + mirror + " with value of " + value)).toJSON()).build();
    }

	@POST
	@Path("{mirror}/retrieveLink")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveLink(@PathParam("mirror") String mirror,
	                       @FormParam("link") String link,
	                       @FormParam("token") String token) {

		if (isLogged(new Token(token))) {
			return Response.ok(new SResponse("ok", searchController.retrieveLink(link, mirror)).toJSON()).build();
		}

		return Response.ok(new SResponse("error", new JSONLightError(3, "Error retrieving link of " + mirror)).toJSON()).build();
	}

	private boolean isLogged(Token _token) {
		return userController.isLogged(_token);
	}
}
