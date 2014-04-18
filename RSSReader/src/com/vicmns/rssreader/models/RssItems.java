package com.vicmns.rssreader.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class RssItems implements Parcelable {
	private List<RssItem> rssItemList;

	public RssItems(){
	}
	
	public List<RssItem> getRssItemList() {
		return rssItemList;
	}

	public void setRssItemList(List<RssItem> rssItemList) {
		this.rssItemList = rssItemList;
	}

	protected RssItems(Parcel in) {
        if (in.readByte() == 0x01) {
            rssItemList = new ArrayList<RssItem>();
            in.readList(rssItemList, RssItem.class.getClassLoader());
        } else {
            rssItemList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rssItemList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(rssItemList);
        }
    }

    public static final Parcelable.Creator<RssItems> CREATOR = new Parcelable.Creator<RssItems>() {
        @Override
        public RssItems createFromParcel(Parcel in) {
            return new RssItems(in);
        }

        @Override
        public RssItems[] newArray(int size) {
            return new RssItems[size];
        }
    };

}
