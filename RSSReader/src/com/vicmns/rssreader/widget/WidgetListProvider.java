package com.vicmns.rssreader.widget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.vicmns.rssreader.R;
import com.vicmns.rssreader.activities.RssItemDetailsActivity;
import com.vicmns.rssreader.models.RssItem;
import com.vicmns.rssreader.models.RssItems;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class WidgetListProvider implements RemoteViewsFactory {
	private Context context;
	private List<RssItem> rssItemList;
	private int appWidgetId;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ImageSize targetImageSize;
	
	private Bitmap bitmap;

	public WidgetListProvider(Context context, Intent intent) {
		this.context = context;
		
		rssItemList = new ArrayList<RssItem>();
		Bundle bundle = intent.getBundleExtra("AdapterBundle");
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.color.grey)
		.showImageForEmptyUri(R.color.grey)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.ARGB_8888)
		.build();
		
		targetImageSize = new ImageSize(70, 70);

		if(bundle != null) {
			appWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			rssItemList = bundle.getParcelableArrayList("RssItems");			
		}
	}

	@Override
	public int getCount() {
		return rssItemList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.rss_simple_list_item);
		
		remoteView.setTextViewText(R.id.rss_list_item_title_textView, rssItemList.get(position).getTitle());
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		remoteView.setTextViewText(R.id.rss_list_item_date_textView,
				df.format(rssItemList.get(position).getPubDate()));
		
		setImageBitmap(position, remoteView);
		
		Intent intent = new Intent(context, RssItemDetailsActivity.class);
		intent.putExtra("RssItem", rssItemList.get(position));
		intent.setAction(Intent.ACTION_SEND);
		
        remoteView.setOnClickFillInIntent(R.id.rss_list_item_layout, intent);

		return remoteView;
	}
	
	private void setImageBitmap(final int position, final RemoteViews remoteView) {
		bitmap = imageLoader.loadImageSync(rssItemList.get(position).getImageURL(), targetImageSize, options);
		
		remoteView.setImageViewBitmap(R.id.rss_list_item_imageView, bitmap);
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {

	}

	@Override
	public void onDestroy() {
		
	}

}
