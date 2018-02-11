package com.jeseromero.core.controller.runnable;


import com.jeseromero.core.model.Torrent;

/**
 * Created by Linkaynn on 20/02/2017.
 * SSTorrent
 */
public class TorrentFiller implements Runnable {
	private Torrent torrent;

	public TorrentFiller(Torrent torrent) {
		this.torrent = torrent;
	}

	@Override
	public void run() {
		torrent.fillData();
	}
}
