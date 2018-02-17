package com.jeseromero.model.lightweight;


import com.jeseromero.core.model.Torrent;

public class JSONLightTorrent {

    private final String name;
    private final String size;
    private final String leechers;
    private final String seeders;
    private final String date;
    private final String url;

    public JSONLightTorrent(Torrent torrent) {
        this.name = torrent.getName();
        this.size = torrent.getSize();
        this.leechers = torrent.getLeechers();
        this.seeders = torrent.getSeeders();
        this.date = torrent.getDate();
        this.url = torrent.getUrl();
    }
}
