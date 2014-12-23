package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BloggerPostList implements Serializable{
	public String nextPageToken;
	public BloggerPostItems[] items;
	
	
	public String getNextPageToken(){
		return this.nextPageToken;
	}
	
	public BloggerPostItems[] getItems(){
		return this.items;
	}
}
