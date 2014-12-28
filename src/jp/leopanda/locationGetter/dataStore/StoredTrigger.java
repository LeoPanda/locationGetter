package jp.leopanda.locationGetter.dataStore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/*
 * デイリートリガ
 * Blogger APIには１日の使用制限があるため
 * 位置情報は１日一回だけAPIからデータストアへ転送し
 * 以降のリクエストではデータストアの情報を利用する。
 */
@Entity
public class StoredTrigger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long key; 
	private String doneDate;
	
	public StoredTrigger(String doneDate){
		this.doneDate = doneDate;
	}
	
	
	public Long getKey() {
		return key;
	}


	public String getDoneDate(){
		return this.doneDate;
	}
}
