package com.vicmns.rssreader.activities;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vicmns.rssreader.R;
import com.vicmns.rssreader.adapters.RssItemsAdapter;
import com.vicmns.rssreader.http.GetRssItems;
import com.vicmns.rssreader.interfaces.GetRssItemsCallbacks;
import com.vicmns.rssreader.interfaces.ListOverlayLayoutCallbacks;
import com.vicmns.rssreader.models.RssItem;
import com.vicmns.rssreader.views.ListOverlayLayoutView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements 
	ListOverlayLayoutCallbacks, OnRefreshListener, GetRssItemsCallbacks, OnItemClickListener {
	private ListOverlayLayoutView listOverlayLayoutView;
	private ListView rssItemsListView;
	private PullToRefreshLayout mPullToRefreshLayout;
	
	private RequestQueue queue;
	private GetRssItems getRssItems;
	
	private RssItemsAdapter rssItemsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeViews();
		initializeRssDownload();
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	private void initializeViews() {
		listOverlayLayoutView = new ListOverlayLayoutView(
				(FrameLayout) findViewById(R.id.list_overlay_layout),
				this);
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
		rssItemsListView = (ListView) findViewById(R.id.main_list_view);
		
		// Now setup the PullToRefreshLayout
	    ActionBarPullToRefresh.from(this)
	            .theseChildrenArePullable(rssItemsListView)
	            .listener(this)
	            .setup(mPullToRefreshLayout);
		rssItemsListView.setVisibility(View.INVISIBLE);
		rssItemsListView.setOnItemClickListener(this);
		listOverlayLayoutView.showLoadingView();
	}
	
	private void initializeRssDownload() {
		getRssItems = new GetRssItems(this, this);
		getRssItems.getNewRssItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onRetryClick() {
		listOverlayLayoutView.showLoadingView();
		listOverlayLayoutView.hideOnConnectionErrorViews();
		initializeRssDownload();
	}

	@Override
	public void onRefreshStarted(View view) {
		initializeRssDownload();
	}

	@Override
	public void onItemsDownloaded(List<RssItem> rssItemList) {
		if(mPullToRefreshLayout.isRefreshing())
			mPullToRefreshLayout.setRefreshComplete();
		
		listOverlayLayoutView.hideOverlayView();
		rssItemsAdapter = new RssItemsAdapter(this, rssItemList);
		rssItemsListView.setAdapter(rssItemsAdapter);
		rssItemsListView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onConnectionError() {
		listOverlayLayoutView.showOnConnectionErrorViews();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RssItem selectedItem = (RssItem) rssItemsAdapter.getItem(position);
		Intent detailsIntent = new Intent(this, RssItemDetailsActivity.class);
		detailsIntent.putExtra("RssItem", selectedItem);
		
		startActivity(detailsIntent);
	}

}
