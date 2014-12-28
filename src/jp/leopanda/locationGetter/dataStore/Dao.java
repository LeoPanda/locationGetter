package jp.leopanda.locationGetter.dataStore;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * データオブジェクト操作
 * @author LeoPanda
 *
 */
public enum Dao {
	INSTANCE;
	/*
	 * 位置情報のリストを取得
	 */
	@SuppressWarnings("unchecked")
	public List<StoredLocation> listStoredLocation(){
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("SELECT lc FROM StoredLocation lc");
		return q.getResultList();
	}
	/*
	 * 位置情報１件分のラベルを取得
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<StoredLabel> getStoredLabels(String id){
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("SELECT lb FROM StoredLabel lb where lb.id = :id");
	    q.setParameter("id", id);
		return q.getResultList();
	}
	/*
	 * ディリートリガ情報を取得
	 */
	@SuppressWarnings("unchecked")
	public List<StoredTrigger> getTrigger(){
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("SELECT tr FROM StoredTrigger tr");
		return q.getResultList();
	}
	
	/*
	 * 位置情報をデータストアに書き込む
	 */
	public void addStoredLocation(String id,String name,String url,Float lat,Float lng,String imgUrl){
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			StoredLocation storedLocation = new StoredLocation(id, name, url, lat, lng, imgUrl);
			em.persist(storedLocation);
			em.close();
			}
	}
	/*
	 * ラベル情報をデータストアに書き込む
	 */
	public void addStoredLabel(String id,String label){
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			StoredLabel storedLabel = new StoredLabel(id, label);
			em.persist(storedLabel);
			em.close();
		}
	}
	/*
	 * ディリートリガ情報をデータストアに書き込む
	 */
	public void addTrigger(String doneDate){
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			StoredTrigger trigger = new StoredTrigger(doneDate);
			em.persist(trigger);
			em.close();
		}
	}
	/*
	 * 位置情報とラベル情報のすべてをデータストアから削除する
	 */
	public void removeAllStored(){
		synchronized (this) {
		EntityManager em = EMFService.get().createEntityManager();
			try{
		        Query q = em.createQuery("DELETE FROM StoredLocation lc");
		        q.executeUpdate();
		        q = em.createQuery("DELETE FROM StoredLabel lb");
		        q.executeUpdate();
			} finally{
				em.close();
			}
		}
	}
	/*
	 * ディリートリガ情報を削除する
	 */
	public void removeTrigger(){
		EntityManager em = EMFService.get().createEntityManager();
		try{
			Query q = em.createQuery("DELETE FROM StoredTrigger tr");
			q.executeUpdate();
		} finally {
			em.close();
		}
	}
}
