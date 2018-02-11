package com.jeseromero.core.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author jrarbelo - Jes� Romero Arbelo
 * @version Revision 1.1
 *          <br> Revision 1.1 -> Versi�n inicial.
 * @since Release 2017
 */
public abstract class Configuration {

	protected String name;

	protected String searchUrl;

	protected String rowSelector;

	protected String magnetSelector;

	protected String directDownloadSelector;

	public abstract String getName();

	public abstract Elements getRows(Document document);

	public abstract Torrent getTorrentLink(Element row);

	public abstract String getMagnetLink(Document document);

	public abstract String getDirectDownloadLink(Document document);

	public abstract String buildSearchUrl(String text, Integer index);
}
