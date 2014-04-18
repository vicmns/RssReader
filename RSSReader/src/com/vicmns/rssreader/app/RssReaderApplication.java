package com.vicmns.rssreader.app;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

public class RssReaderApplication extends Application {

	
	@Override
	public void onCreate() {
		//File cacheDir = StorageUtils.getCacheDirectory(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 85, null)
		.discCacheFileCount(300)
		.writeDebugLogs()
		.build();
		
		ImageLoader.getInstance().init(config);
		super.onCreate();
	}
	

}
