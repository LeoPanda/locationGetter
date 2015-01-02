package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;
/**
 * 投稿記事　位置情報
 * @author LeoPanda
 *
 */
@SuppressWarnings("serial")
public class BloggerPostItemLocation implements Serializable{
	public String name;
	public Double lat;
	public Double lng;
	public String span;
	
	public String getName() {
		return name;
	}
	public Double getLat() {
		return lat;
	}
	public Double getLng() {
		return lng;
	}
	public String getSpan(){
		return span;
	}

}
