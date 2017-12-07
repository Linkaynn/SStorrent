package com.jeseromero.resources;

import com.google.gson.Gson;
import com.jeseromero.controller.SearchController;
import com.jeseromero.controller.UserController;
import com.jeseromero.model.lightweight.JSONLightTorrent;
import com.jeseromero.sstorrent.core.model.Torrent;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("search")
public class SearchResource {

    @Inject
    private SearchController searchController;

    @Inject
    private UserController userController;

    @GET
    @Path("{mirror}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("mirror") String mirror,
                           @PathParam("value") String value,
                           @QueryParam("token") String token) {

        if (userController.isLogged(token)) {
            Collection<Torrent> torrents = searchController.search(mirror, value);

            return Response.ok()
                    .entity(new Gson().toJson(torrents.stream().map(JSONLightTorrent::new).collect(Collectors.toList())))
                    .build();
        }

        return Response.status(403).build();
    }
}
