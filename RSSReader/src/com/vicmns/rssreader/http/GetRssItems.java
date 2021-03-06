package com.vicmns.rssreader.http;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vicmns.rssreader.interfaces.GetRssItemsCallbacks;
import com.vicmns.rssreader.models.RssItem;

public class GetRssItems {
	private RequestQueue queue;
	private Context context;
	private GetRssItemsCallbacks rssItemsCallbacks;
	private Calendar cal = Calendar.getInstance();

	public GetRssItems(Context context, GetRssItemsCallbacks rssItemsCallbacks) {
		this.context = context;
		queue = Volley.newRequestQueue(context);
		this.rssItemsCallbacks = rssItemsCallbacks;
	}

	public void getNewRssItems() {
		StringRequest rssStringItems = new StringRequest(
				"http://www.engadget.com/rss.xml", new Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							Log.i(this.getClass().getSimpleName(), "Service ok!...");
							rssItemsCallbacks.onItemsDownloaded(parseStringResponse(response));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i(this.getClass().getSimpleName(), "Service error!...");
						rssItemsCallbacks.onConnectionError();
					}
				});
		//rssStringItems.setTag(this);
		Log.i(this.getClass().getSimpleName(), "Downloading feed!...");
		queue.add(rssStringItems);
//		AssetManager assetManager = context.getAssets();
//		InputStream ims;
//		try {
//			ims = assetManager.open("testRss.xml");
//			rssItemsCallbacks.onItemsDownloaded(parseStringResponse(ims));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	public void cancelWSCall() {
		queue.cancelAll(this);
	}

	private List<RssItem> parseStringResponse(String response)
			throws Exception {
		List<RssItem> rssItemList = new ArrayList<RssItem>();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		String tagName;
		//parser.setInput(inputStream, "UTF_8");
		parser.setInput(new StringReader(response));
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			tagName = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (tagName.equals("item")) {
					rssItemList.add(parseXMLItem(parser));
				}  else if (tagName.equals("pubDate")) {
					// Mon, 14 Apr 2014 11:00:00 -0400
					SimpleDateFormat sdf = new SimpleDateFormat(
							"EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
					cal.setTime(sdf.parse(parser.nextText().toString()));// all
																			// done
					rssItemList.get(rssItemList.size() - 1).setPubDate(cal.getTime());
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = parser.next();
		}

		return rssItemList;
	}

	private RssItem parseXMLItem(XmlPullParser parser) throws Exception {
		RssItem rssItem = new RssItem();
		String tagName;
		int eventType = parser.getEventType();
		Document doc;
		String itemDescription;
		while (eventType != XmlPullParser.END_TAG) {
			tagName = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (tagName.equals("title")) {
					rssItem.setTitle(parser.nextText().toString());
				} else if (tagName.equals("link")) {
					rssItem.setLink(parser.nextText().toString());
				} else if (tagName.equals("description")) {
					itemDescription = stripCDATA(parser.nextText());
					doc = Jsoup.parse(itemDescription);
					Element image = doc.select("img").first();
					if(image != null)
						rssItem.setImageURL(image.absUrl("src"));
					itemDescription = itemDescription.replaceAll("<img(.|\n)*?>","");
					rssItem.setDescription(itemDescription.trim());
				} else if (tagName.equals("dc:creator")) {

				}
				break;
			}
			eventType = parser.next();
		}

		return rssItem;
	}
	
	private static String stripCDATA(String s) {
	    s = s.trim();
	    if (s.startsWith("<![CDATA[")) {
	      s = s.substring(9);
	      int i = s.indexOf("]]&gt;");
	      if (i == -1) {
	        throw new IllegalStateException(
	            "argument starts with <![CDATA[ but cannot find pairing ]]&gt;");
	      }
	      s = s.substring(0, i);
	    }
	    return s;
	  }
}
