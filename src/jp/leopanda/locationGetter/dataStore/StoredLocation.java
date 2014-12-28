package jp.leopanda.locationGetter.dataStore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 投稿記事位置情報
 * @author LeoPanda
 *
 */
@Entity
public class StoredLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long key;  	//unique key for GAE DataStore
	private String id;	//for identify post data.
	private String name;
	private String url;
	private Float lat;
	private Float lng;
	private String imgUrl;
	
	public StoredLocation(String id,String name,String url,Float lat,Float lng,String imgUrl){
		this.id = id;
		this.name = name;
		this.url = url;
		this.lat = lat;
		this.lng = lng;
		this.imgUrl = imgUrl;
	}

	public Long getKey(){
		return key;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Float getLat() {
		return lat;
	}

	public Float getLng() {
		return lng;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	
}
