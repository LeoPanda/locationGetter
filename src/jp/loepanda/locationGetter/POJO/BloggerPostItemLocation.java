package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BloggerPostItemLocation implements Serializable{
	public String name;
	public Float lat;
	public Float lng;
	public String span;
	
	public String getName() {
		return name;
	}
	public Float getLat() {
		return lat;
	}
	public Float getLng() {
		return lng;
	}
	public String getSpan(){
		return span;
	}

}
