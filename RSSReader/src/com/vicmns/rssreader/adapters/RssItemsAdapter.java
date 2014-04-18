package com.vicmns.rssreader.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vicmns.rssreader.R;
import com.vicmns.rssreader.models.RssItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssItemsAdapter extends BaseAdapter {	
	private Context context;
	private LayoutInflater mInflater;
	private List<RssItem> rssItemList;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	
	public RssItemsAdapter(Context context, List<RssItem> rssItemList) {
		this.context = context;
		this.rssItemList = rssItemList;
		this.mInflater = LayoutInflater.from(context);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.color.grey)
		.showImageForEmptyUri(R.color.grey)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.ARGB_8888)
		.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rssItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return rssItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.rss_list_item, null);
			holder.rssItemImageIv = (ImageView) convertView.findViewById(R.id.rss_list_item_imageView);
			holder.rssItemTitleTv = (TextView) convertView.findViewById(R.id.rss_list_item_title_textView);
			holder.rssItemDateTv = (TextView) convertView.findViewById(R.id.rss_list_item_date_textView);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setListItemsViews(holder, position);
		
		return convertView;
	}
	
	private void setListItemsViews(ViewHolder holder, int position) {
		holder.rssItemTitleTv.setText(rssItemList.get(position).getTitle());
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		holder.rssItemDateTv.setText(df.format(rssItemList.get(position).getPubDate()));
		
		Log.i(this.getClass().getSimpleName(), rssItemList.get(position).getImageURL());
		imageLoader.displayImage( rssItemList.get(position).getImageURL(), holder.rssItemImageIv, options);
	}
	
	private class ViewHolder {
		ImageView rssItemImageIv;
		TextView rssItemTitleTv;
		TextView rssItemDateTv;
	}

}
