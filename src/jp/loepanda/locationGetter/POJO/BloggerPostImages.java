package jp.loepanda.locationGetter.POJO;

import java.io.Serializable;
/**
 * 投稿記事　画像URL
 * @author LeoPanda
 *
 */
@SuppressWarnings("serial")
public class BloggerPostImages  implements Serializable{
	public String url;

	public String getUrl() {
		return url;
	}
}
