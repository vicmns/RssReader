package com.vicmns.rssreader.services;

import java.util.ArrayList;
import java.util.List;

import com.vicmns.rssreader.http.GetRssItems;
import com.vicmns.rssreader.http.GetWidgetRssItems;
import com.vicmns.rssreader.interfaces.GetRssItemsCallbacks;
import com.vicmns.rssreader.models.RssItem;

import android.app.IntentService;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

public class GetRssItemsService extends IntentService implements  GetRssItemsCallbacks {
	public static final String BUNDLE_TAG = "ServiceBundle"; 
	public static final String WIDGET_ID = AppWidgetManager.EXTRA_APPWIDGET_ID;
	public static final String NOTIFICATION = "com.vicmns.rssreader.service.receiver";
	public static final String RSS_ITEMS_TAG = "RssItems";
	
	private GetRssItems getRssItems;
	private int appWidgetId = -1;
	
	public GetRssItemsService() {
		super("GetRssItemsService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle bundle = intent.getBundleExtra(BUNDLE_TAG);
		if(bundle != null)
			appWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		
		Log.i(this.getClass().getSimpleName(), "Service initialized!...");
		
		getRssItems = new GetRssItems(this, this);
		getRssItems.getNewRssItems();
	}

	@Override
	public void onItemsDownloaded(List<RssItem> rssItemList) {
		Log.i(this.getClass().getSimpleName(), "Downloading was a success...");
		publishResults(rssItemList);
	}

	@Override
	public void onDestroy() {
		if(getRssItems != null)
			getRssItems.cancelWSCall();
		super.onDestroy();
	}

	@Override
	public void onConnectionError() {
		Log.i(this.getClass().getSimpleName(), "Connection error retrying...");
//		try {
//			Thread.sleep(3000);
//			getRssItems.getNewRssItems();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	 private void publishResults(List<RssItem> rssItemList) {
		    Intent intent = new Intent(NOTIFICATION);
		    Bundle bundle = new Bundle();
			bundle.setClassLoader(RssItem.class.getClassLoader());
			bundle.putParcelableArrayList("RssItems", (ArrayList<? extends Parcelable>) rssItemList);
			bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			intent.putExtra("AdapterBundle",bundle);
			
			Log.i(this.getClass().getSimpleName(), "Send Broadcast");
		    sendBroadcast(intent);
	 }

}
