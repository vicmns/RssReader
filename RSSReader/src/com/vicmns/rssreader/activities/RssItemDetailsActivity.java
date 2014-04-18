package com.vicmns.rssreader.activities;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vicmns.rssreader.R;
import com.vicmns.rssreader.models.RssItem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class RssItemDetailsActivity extends ActionBarActivity {
	private ImageView rssItemImageIV;
	private TextView rssItemDescriptionTV;
	
	private RssItem rssItem;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.color.grey)
		.showImageForEmptyUri(R.color.grey)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.ARGB_8888)
		.build();
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			rssItem = bundle.getParcelable("RssItem");
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			setContentView(R.layout.activity_rss_item_details);
			initializeViews();
			setViews();
		} else
			finish();
		
	}
	
	private void initializeViews() {
		rssItemImageIV = (ImageView) findViewById(R.id.rss_item_details_imageView);
		rssItemDescriptionTV = (TextView) findViewById(R.id.rss_item_details_description_textView);
	}
	
	private void setViews() {
		imageLoader.displayImage( rssItem.getImageURL(), rssItemImageIV, options);
		rssItemDescriptionTV.setText(rssItem.getDescription());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	    case android.R.id.home:
	    	Intent upIntent = NavUtils.getParentActivityIntent(this);
	        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	            TaskStackBuilder.create(this)
	                    // Add all of this activity's parents to the back stack
	                    .addNextIntentWithParentStack(upIntent)
	                    // Navigate up to the closest parent
	                    .startActivities();
	        } else {
	            // This activity is part of this app's task, so simply
	            // navigate up to the logical parent activity.
	            NavUtils.navigateUpTo(this, upIntent);
	        }
	        return true;
	        
	        /*
	         * Intent homeIntent = new Intent(this, HomeActivity.class);
	         * homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	         * startActivity(homeIntent);
	         */
	    }
	  return (super.onOptionsItemSelected(menuItem));
	}
	
	
}
