package jp.loepanda.locationGetter.POJO;

import java.util.List;

public class ResultLocation {
	private String id;
	public String name;
	public String url;
	public Double lat;
	public Double lng;
	public String imgUrl;
	public List<String> labels;
	
	public ResultLocation(){
		this.id = "";
		this.name = "";
		this.url = "";
		this.lat = 0d;
		this.lng = 0d;
		this.imgUrl = "";
	}
	public String getid(){
		return id;
	}
	
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public Double getLat() {
		return lat;
	}
	public Double getLng() {
		return lng;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public List<String> getLabels() {
		return labels;
	}

	public void setId(String id){
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}


}
