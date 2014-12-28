package jp.leopanda.locationGetter.dataStore;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * For GAE(google application engine) DataStore Service
 * @author LeoPanda
 *
 */
public class EMFService {
	  private static final EntityManagerFactory emfInstance = Persistence
		      .createEntityManagerFactory("transactions-optional");

		  private EMFService() {
		  }

		  public static EntityManagerFactory get() {
		    return emfInstance;
		  }
}
