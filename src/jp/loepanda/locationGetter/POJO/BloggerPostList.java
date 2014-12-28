package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;
/**
 * 投稿記事リスト
 * @author LeoPanda
 *
 */
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
