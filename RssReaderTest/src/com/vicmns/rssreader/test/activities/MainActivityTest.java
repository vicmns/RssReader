package com.vicmns.rssreader.test.activities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.vicmns.rssreader.R;
import com.vicmns.rssreader.activities.MainActivity;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import static org.junit.Assert.assertTrue;
import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	private Activity activity;
	
	@Before
	public void setup() {
		activity = Robolectric.buildActivity(MainActivity.class).create().visible().start().resume().get();
	}
	
	@Test
	public void activityOpens() {
		assertThat(activity).isNotNull();
	}
	
	@Test
	public void activityViewsInitState() {
		ListView listView = (ListView) activity.findViewById(R.id.main_list_view);
		assertThat(listView).isNotNull();
		assertThat(listView).isInvisible();
		
		ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.list_overlay_progressbar);
		assertThat(progressBar).isNotNull();
		assertThat(progressBar).isVisible();
		
		LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.list_overlay_retry_layout);
		assertThat(linearLayout).isNotNull();
		assertThat(linearLayout).isGone();
	}
}