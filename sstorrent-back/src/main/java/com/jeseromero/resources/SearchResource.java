package com.jeseromero.resources;

import com.jeseromero.controller.SearchController;
import com.jeseromero.controller.UserController;
import com.jeseromero.core.model.Torrent;
import com.jeseromero.model.Token;
import com.jeseromero.model.lightweight.JSONError;
import com.jeseromero.model.lightweight.JSONLightTorrent;
import com.jeseromero.model.lightweight.JSONLightTorrents;
import com.jeseromero.model.lightweight.SResponse;

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

        if (userController.isLogged(new Token(token))) {

            // TODO: Index as PathParam
            Collection<Torrent> torrents = searchController.search(mirror, value, 0);

            if (torrents == null || torrents.isEmpty()) {
                return Response.ok(new SResponse("ok", null).toJSON()).build();
            } else {
                return Response.ok()
                        .entity(new SResponse("ok", new JSONLightTorrents(torrents.stream().map(JSONLightTorrent::new).collect(Collectors.toList()))).toJSON())
                        .build();
            }
        }

        return Response.ok(new SResponse("error", new JSONError(2, "Error searching torrents of " + mirror + " with value of " + value)).toJSON()).build();
    }
}
