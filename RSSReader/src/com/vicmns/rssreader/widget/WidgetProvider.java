package com.vicmns.rssreader.widget;

import com.vicmns.rssreader.R;
import com.vicmns.rssreader.services.GetRssItemsService;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getBundleExtra("AdapterBundle");
	      if (bundle != null) {
	    	  int appWidgetId = bundle.getInt(
	  				AppWidgetManager.EXTRA_APPWIDGET_ID,
	  				AppWidgetManager.INVALID_APPWIDGET_ID);
	    	  Log.i(this.getClass().getSimpleName(), "appWidgetId: " + appWidgetId);
	    	  updateWidget(context, appWidgetId, bundle);
	      }
	      
	      super.onReceive(context, intent);
	}
	
	

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		/*
		 * int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen
		 */
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; ++i) {
			Intent intent = new Intent(context, GetRssItemsService.class);
		    Bundle bundle = new Bundle();
		    bundle.putInt(GetRssItemsService.WIDGET_ID, appWidgetIds[N - 1]);
		    intent.putExtra(GetRssItemsService.BUNDLE_TAG, bundle);
		    
		    context.startService(intent);
		}
	}
	
	
	private void updateWidget(Context context, int appWidgetId, Bundle bundle) {
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		RemoteViews remoteViews = updateWidgetListView(context,
				appWidgetId, bundle);
        manager.updateAppWidget(appWidgetId, remoteViews);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private RemoteViews updateWidgetListView(Context context, int appWidgetId, Bundle bundle) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_main_layout);
		Intent svcIntent = new Intent(context, WidgetService.class);
		svcIntent.putExtra("AdapterBundle",bundle);
		svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
		if(Build.VERSION.SDK_INT < 14)
			remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list_view,
					svcIntent);
		else
			remoteViews.setRemoteAdapter(R.id.widget_list_view, svcIntent);
		
		remoteViews.setViewVisibility(R.id.widget_loading_indicator, View.GONE);
		remoteViews.setEmptyView(R.id.widget_main_layout, R.id.widget_empty_view_layout);
		
		return remoteViews;
	}

}
