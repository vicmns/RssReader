package com.vicmns.rssreader.views;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vicmns.rssreader.R;
import com.vicmns.rssreader.interfaces.ListOverlayLayoutCallbacks;

public class ListOverlayLayoutView {
	private FrameLayout listOverlayLayout;
	private ProgressBar listOverlayprogressBar;
	private LinearLayout listOverlayRetryLayout;
	private TextView listOverlayTextView;
	private Button listOverlayButton;
	private ListOverlayLayoutCallbacks lOverlayLayoutCallbacks;
	
	public ListOverlayLayoutView(FrameLayout listOverlayLayout, 
			ListOverlayLayoutCallbacks lOverlayLayoutCallbacks) {
		
		this.listOverlayLayout = listOverlayLayout;
		this.listOverlayprogressBar = (ProgressBar) listOverlayLayout.findViewById(R.id.list_overlay_progressbar);
		this.listOverlayRetryLayout = (LinearLayout) listOverlayLayout.findViewById(R.id.list_overlay_retry_layout);
		this.listOverlayTextView = (TextView) listOverlayLayout.findViewById(R.id.list_overlay_retry_text_view);
		this.listOverlayButton = (Button) listOverlayLayout.findViewById(R.id.list_overlay_retry_button);
		
		this.lOverlayLayoutCallbacks = lOverlayLayoutCallbacks;
		
		initializeViews();
	}
	
	private void initializeViews() {
		listOverlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lOverlayLayoutCallbacks.onRetryClick();
			}
		});
	}
	
	public void showLoadingView() {
		listOverlayprogressBar.setVisibility(View.VISIBLE);
		listOverlayRetryLayout.setVisibility(View.GONE);
	}
	
	public void showOnConnectionErrorViews() {
		listOverlayprogressBar.setVisibility(View.GONE);
		listOverlayRetryLayout.setVisibility(View.VISIBLE);
	}
	
	public void hideOverlayView() {
		listOverlayLayout.setVisibility(View.GONE);
	}
	
	public void hideLoadingView() {
		listOverlayprogressBar.setVisibility(View.GONE);
		listOverlayRetryLayout.setVisibility(View.GONE);
	}
	
	public void hideOnConnectionErrorViews() {
		listOverlayRetryLayout.setVisibility(View.GONE);
	}
}
