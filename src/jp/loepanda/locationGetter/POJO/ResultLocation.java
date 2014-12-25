package jp.loepanda.locationGetter.POJO;

public class ResultLocation {
	public String name;
	public String url;
	public Float lat;
	public Float lng;
	public String imgUrl;
	public String[] labels;
	
	public ResultLocation(){
		this.name = "";
		this.url = "";
		this.lat = 0f;
		this.lng = 0f;
		this.imgUrl = "";
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
	public String[] getLabels() {
		return labels;
	}


	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}


}
