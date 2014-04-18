package com.vicmns.rssreader.models;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class RssItem implements Parcelable {
	private String title;
	private String link;
	private String description;
	private String imageURL;
	
	private Date pubDate;
	
	public RssItem() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(link);
		dest.writeString(description);
		dest.writeString(imageURL);
		
		dest.writeLong(pubDate != null ? pubDate.getTime() : -1L);
	}

	public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>() {
		public RssItem createFromParcel(Parcel in) {
			return new RssItem(in);
		}

		public RssItem[] newArray(int size) {
			return new RssItem[size];
		}
	};

	private RssItem(Parcel in) {
		title = in.readString();
        link = in.readString();
        description = in.readString();
        imageURL = in.readString();
        long tmpPubDate = in.readLong();
        pubDate = tmpPubDate != -1 ? new Date(tmpPubDate) : null;
	}
}
