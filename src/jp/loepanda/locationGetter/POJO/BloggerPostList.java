package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BloggerPostList implements Serializable{
	public String nextPageToken;
	public BloggerPostItem[] items;
	
	
	public String getNextPageToken(){
		return this.nextPageToken;
	}
	
	public BloggerPostItem[] getItems(){
		return this.items;
	}
}
