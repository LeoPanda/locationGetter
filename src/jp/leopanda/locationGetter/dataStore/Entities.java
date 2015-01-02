package jp.leopanda.locationGetter.dataStore;
/**
 * データストア上のエンティティ一覧
 * @author LeoPanda
 *
 */
public enum Entities {
	LOCATION("Location"),
	TRIGGER("Trigger"),
	OLD_LOCATION("StoredLocation"),
	OLD_LABEL("StoredLabel"),
	OLD_TRIGGER("StoredTrigger");

	private String name;

	Entities(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}	

}
