package jp.leopanda.locationGetter.dataStore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 投稿記事ラベル情報
 * @author LeoPanda
 *
 */
@Entity
public class StoredLabel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long key; 	//unique key for GAE DataStore.
	private String id; 	//for identify post data.
	private String label;
	
	public StoredLabel(String id,String label){
		this.id = id;
		this.label = label;
	}

	public Long getKey() {
		return key;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}
}
