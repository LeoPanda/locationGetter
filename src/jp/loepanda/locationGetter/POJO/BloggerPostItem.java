package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;

/**
 * 投稿記事
 * @author LeoPanda
 *
 */
@SuppressWarnings("serial")
public class BloggerPostItem implements Serializable{
		public String id;
		public String url;
		public String title;
		public BloggerPostItemLocation location;
		public BloggerPostImages[] images;
		public String[] labels;
		
		public String getId(){
			return id;
		}

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
		public String[] getLabels() {
			return labels;
		}

}
