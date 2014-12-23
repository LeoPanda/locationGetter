package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BloggerPostItems implements Serializable{
		public String url;
		public String title;
		public BloggerPostItemLocation location;
		public BloggerPostImages[] images;
		
		public String getUrl() {
			return url;
		}
		public String getTitle() {
			return title;
		}
		public BloggerPostItemLocation getLocation() {
			return location;
		}
		public BloggerPostImages[] getImages() {
			return images;
		}

}
