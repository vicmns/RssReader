package com.vicmns.rssreader.interfaces;

import java.util.List;

import com.vicmns.rssreader.models.RssItem;

public interface GetRssItemsCallbacks {
	public void onItemsDownloaded(List<RssItem> rssItemList);
	public void onConnectionError();
}
