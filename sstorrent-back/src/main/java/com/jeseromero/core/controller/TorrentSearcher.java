package com.jeseromero.core.controller;


import com.jeseromero.core.controller.runnable.TorrentLoader;
import com.jeseromero.core.model.Configuration;
import com.jeseromero.core.model.Torrent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TorrentSearcher {

    private Collection<Configuration> configurations;

    public TorrentSearcher(Collection<Configuration> configurations) {
        this.configurations = configurations;
    }

    public TorrentSearcher(Configuration configuration) {
        this(Collections.singletonList(configuration));
    }

    public HashMap<Configuration, Collection<Torrent>> search(String text, int index) {

    	if (configurations == null) {
            configurations = new ConfigurationController().allConfigurations();
        }

        HashMap<Configuration, Collection<Torrent>> torrents = new HashMap<>();

        HashMap<Configuration, Future<Collection<Torrent>>> results = new HashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(configurations.size());

        for (Configuration configuration : configurations) {
            TorrentLoader torrentLoader = new TorrentLoader(configuration, text, index);

            Future<Collection<Torrent>> collectionFuture = executorService.submit(torrentLoader);

            results.put(configuration, collectionFuture);
        }

        for (Map.Entry<Configuration, Future<Collection<Torrent>>> futureEntry : results.entrySet()) {
            try {
                torrents.put(futureEntry.getKey(), futureEntry.getValue().get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return torrents;
    }

}
