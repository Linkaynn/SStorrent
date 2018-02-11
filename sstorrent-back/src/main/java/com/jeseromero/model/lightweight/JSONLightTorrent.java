package com.jeseromero.model.lightweight;


import com.jeseromero.core.model.Torrent;

public class JSONLightTorrent {

    private final String name;
    private final String size;
    private final String leechers;
    private final String seeders;
    private final String date;
    private final String magnet;
    private final String directDownloadURL;
    private final String url;

    public JSONLightTorrent(Torrent torrent) {
        this.name = torrent.getName();
        this.size = torrent.getSize();
        this.leechers = torrent.getLeechers();
        this.seeders = torrent.getSeeders();
        this.date = torrent.getDate();
        this.magnet = torrent.getMagnetLink();
        this.directDownloadURL = torrent.getDirectDownloadLink();
        this.url = torrent.getUrl();
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getLeechers() {
        return leechers;
    }

    public String getSeeders() {
        return seeders;
    }

    public String getDate() {
        return date;
    }

    public String getMagnet() {
        return magnet;
    }

    public String getDirectDownloadURL() {
        return directDownloadURL;
    }

    public String getUrl() {
        return url;
    }
}
