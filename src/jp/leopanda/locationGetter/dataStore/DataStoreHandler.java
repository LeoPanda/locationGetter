package jp.leopanda.locationGetter.dataStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.loepanda.locationGetter.POJO.ResultLocation;
/**
 * データストア　ハンドリング用サービス
 * @author LeoPanda
 *
 */
public class DataStoreHandler {
 	/*
 	 * 今日最初のリクエストかどうかの判定を行い
 	 * 同時にデータストアにリクエスト実行済みのフラグを格納する
 	 */
 	public boolean checkTriggerd(){
		boolean isTriggerd = false;
 		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat day = new SimpleDateFormat("yy-MM-dd");
		String today = day.format(calendar.getTime());
		Dao dao = Dao.INSTANCE;
		List<StoredTrigger> triggers = dao.getTrigger();
		if(triggers.size() == 0){
			dao.addTrigger(today);
			isTriggerd = true;
		}else if(!triggers.get(0).getDoneDate().equals(today)){
			dao.removeTrigger();
			dao.addTrigger(today);
			isTriggerd = true;
		}
		return isTriggerd;
 	}

	/**
	 * 位置情報をデータストアに格納する
	 * @param locations
	 */
	public void setToDataStore(List<ResultLocation> locations){
		Dao dao = Dao.INSTANCE;
		dao.removeAllStored();
		for (ResultLocation location : locations) {
			dao.addStoredLocation(location.getid(), location.getName(), location.getUrl(),
								location.getLat(), location.getLng(), location.getImgUrl());
			String[] labels = location.getLabels();
			for(String label:labels){
				dao.addStoredLabel(location.getid(), label);
			}
		}
	}
	
	/**
	 * 位置情報をデータストアから取り出しPOJOにセットする
	 */
	public List<ResultLocation> getFromDataStore(){
		List<ResultLocation> resultLocations = new ArrayList<ResultLocation>();
		Dao dao = Dao.INSTANCE;
		List<StoredLocation> storedLocations = dao.listStoredLocation();
		for(StoredLocation storedLocation:storedLocations){
			ResultLocation resultLocation = new ResultLocation();
			resultLocation.setId(storedLocation.getId());
			resultLocation.setImgUrl(storedLocation.getImgUrl());
			resultLocation.setLat(storedLocation.getLat());
			resultLocation.setLng(storedLocation.getLng());
			resultLocation.setName(storedLocation.getName());
			resultLocation.setUrl(storedLocation.getUrl());
			resultLocation.setLabels(getLabelsFromStore(dao,storedLocation.getId()));
			resultLocations.add(resultLocation);
		}
		return resultLocations;
	}
    /**
     * 位置データ１件分のラベル情報配列を取り出す
     * @param dao
     * @param id
     * @return
     */
	private String[] getLabelsFromStore(Dao dao,String id) {
		List<String> resultLabels = new ArrayList<String>();
		List<StoredLabel> storedLabels = dao.getStoredLabels(id);
		for(StoredLabel storedLabel:storedLabels){
			resultLabels.add(storedLabel.getLabel());
		}
		return resultLabels.toArray(new String[0]);
	}


}
