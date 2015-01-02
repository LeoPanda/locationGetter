package jp.leopanda.locationGetter.dataStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import jp.loepanda.locationGetter.POJO.ResultLocation;

/**
 * データストア操作用サービス（GAE low-level API version）
 * @author LeoPanda
 *
 */
public class DataStoreHandler {
	/*
	 * Entity Attributes
	 */
	//Location
	final String LOCATION_NAME="name";
	final String LOCATION_URL="url";
	final String LOCATION_LAT="lat";
	final String LOCATION_LNG="lng";
	final String LOCATION_IMGURL="imgurl";
	final String LOCATION_LABEL="label";
	//Trigger
	final String TRIGGER_DONEDATE = "doneDate";
	
	/*
	 * 今日最初のリクエストかどうかの判定を行い
 	 * データストアにリクエスト実行済みのフラグを格納する
	 */
	public boolean checkTriggerd(){
		//本日日付の作成
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
 		calendar.add(Calendar.HOUR, 9);
		String today = new SimpleDateFormat("yy-MM-dd").format(calendar.getTime());
		//データストアのトリガ情報を操作
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = ds.prepare(new Query(Entities.TRIGGER.getName()));
		for (Entity entity:pq.asIterable()){
			String doneDateValue = (String)entity.getProperty(TRIGGER_DONEDATE);
			if(doneDateValue.equals(today)){
				return false;  //当日中は実行させない
			}else{
				ds.delete(entity.getKey());
			}
		}
		Entity newEntity = new Entity(Entities.TRIGGER.getName());
		newEntity.setProperty(TRIGGER_DONEDATE, today);
		ds.put(newEntity);
		return true;
	}
	public DataStoreHandler() {
	}
	/*
	 * 位置情報をデータストアに格納する
	 */
	public void setToDataStore(List<ResultLocation> locations){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		for(ResultLocation location:locations){
			Entity entity = new Entity(Entities.LOCATION.getName());
				entity.setProperty(LOCATION_NAME,location.getName());
				entity.setProperty(LOCATION_URL,location.getUrl());
				entity.setProperty(LOCATION_LAT,location.getLat());
				entity.setProperty(LOCATION_LNG,location.getLng());
				entity.setProperty(LOCATION_IMGURL,location.getImgUrl());
				entity.setProperty(LOCATION_LABEL,location.getLabels());
			ds.put(entity);				
		}
	}
	/**
	 * 位置情報をデータストアから取り出しPOJOにセットする
	 * @return ResultLocationのリスト
	 */
	@SuppressWarnings("unchecked")
	public List<ResultLocation> getFromDataStore(){
		List<ResultLocation> resultLocations = new ArrayList<ResultLocation>();
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = ds.prepare(new Query(Entities.LOCATION.getName()));
		for (Entity entity:pq.asIterable()){
			ResultLocation result = new ResultLocation();
			result.setName((String) entity.getProperty(LOCATION_NAME));
			result.setUrl((String) entity.getProperty(LOCATION_URL));
			result.setLat((Double) entity.getProperty(LOCATION_LAT));
			result.setLng((Double) entity.getProperty(LOCATION_LNG));
			result.setImgUrl((String) entity.getProperty(LOCATION_IMGURL));
			result.setLabels((List<String>) entity.getProperty(LOCATION_LABEL));
			resultLocations.add(result);
		}
		return resultLocations;
	}
	/*
	 * 指定のエンティティを削除する
	 */
	public void removeEntity(String entityName){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = ds.prepare(new Query(entityName));
		for (Entity entity:pq.asIterable()){
			ds.delete(entity.getKey());
		}
	}
}
