package jp.leopanda.locationGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.leopanda.locationGetter.dataStore.Dao;
import jp.leopanda.locationGetter.dataStore.DataStoreHandler;
import jp.loepanda.locationGetter.POJO.ResultLocation;

@SuppressWarnings("serial")
public class Servlet extends HttpServlet {
	
	 	final boolean isTest = false;

	 	@Override
	 	/*
	 	 * DoGet
	 	 * http://(デプロイサーバー)/test
	 	 * パラメータ:
	 	 * callback:JSONPコールバック関数を指定
	 	 * reset: (true or false) Blogger APIからデータを取得するように指定
	 	 * resetall: (true or false) データストアの全情報を削除する 
	 	 */
		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
	 		BloggerService blogService = new BloggerService();
	 		DataStoreHandler dataStoreHandler = new DataStoreHandler();
	 		List<ResultLocation> locations = new  ArrayList<ResultLocation>();
	 		String JSON  = "application/json;charset=utf-8";
	 		String JSONP = "text/javascript;charset=utf-8";

	 		//パラメータがあればディリートリガをクリア
	 		String reset = req.getParameter("reset");
	 		if(reset != null){
	 			Dao.INSTANCE.removeTrigger();
	 		}
	 		//今日最初のリクエストならBlogger APIから
	 		//そうでなければデータストアから位置情報を取得しレスポンスデータを作る
	 		boolean isTriggerd = dataStoreHandler.checkTriggerd();
			if(isTriggerd){
				locations = blogService.getLocationList(isTest);
			}else{
				locations =	dataStoreHandler.getFromDataStore();
			}
	
			String responseBody = blogService.getLocationJson(locations);
			String callBack = req.getParameter("callback");  // get request parameter for JSONP
			//データストアの書き込み前にレスポンスを送信
			if(callBack != null){
				resp.setContentType(JSONP);
				resp.getWriter().print(callBack + "(" + responseBody + ");");
			}else{
				resp.setContentType(JSON);
				resp.getWriter().print(responseBody);				
			}
			//データストアへの書き込み
			if(isTriggerd){
				dataStoreHandler.setToDataStore(locations);
			}
			//パラメータがあればデータストアの全情報をクリア
	 		String resetall = req.getParameter("resetall");
	 		if(resetall != null){
	 			Dao.INSTANCE.removeTrigger();
	 			Dao.INSTANCE.removeAllStored();
	 		}

	 	}
	 	
		public void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
		}
}
