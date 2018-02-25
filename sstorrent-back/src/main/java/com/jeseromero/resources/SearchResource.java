package com.jeseromero.resources;

import com.jeseromero.controller.SearchController;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.User;
import com.jeseromero.model.lightweight.JSONLightError;
import com.jeseromero.model.lightweight.JSONLightTorrent;
import com.jeseromero.model.lightweight.JSONLightTorrents;
import com.jeseromero.resources.responses.SResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("search")
public class SearchResource extends SResource{

	private final SearchController searchController = SearchController.instance();

    @GET
    @Path("{mirror}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("mirror") String mirror,
                           @PathParam("value") String value,
                           @QueryParam("token") String token) {

	    User user = getUser(token);

	    if (user != null) {

		    logger.info(user.getUsername(),"New search in %s of %s", mirror, value);

	    	Collection<Torrent> torrents = searchController.search(mirror, value, 0);

		    JSONLightTorrents data = new JSONLightTorrents();

	        if (torrents != null && !torrents.isEmpty()) {
	            data = new JSONLightTorrents(torrents.stream().map(JSONLightTorrent::new).collect(Collectors.toList()));
	        }

	        try {

		        searchController.registerSearch(user, value);
		        logger.info(user.getUsername(), "New search of %s with %d results in %s.", value, data.size(), mirror);

	        } catch (IllegalStateException exception) {
	        	logger.error(exception);
	        }

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

		User user = getUser(token);

		if (user != null) {
			return Response.ok(new SResponse("ok", searchController.retrieveLink(link, mirror)).toJSON()).build();
		}

		return Response.ok(new SResponse("error", new JSONLightError(3, "Error retrieving link of " + mirror)).toJSON()).build();
	}
}
