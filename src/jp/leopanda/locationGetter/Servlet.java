package jp.leopanda.locationGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.leopanda.locationGetter.dataStore.DataStoreHandler;
import jp.leopanda.locationGetter.dataStore.Entities;
import jp.loepanda.locationGetter.POJO.ResultLocation;

@SuppressWarnings("serial")
/**
 * Bloggerの記事から位置情報を読み取って返す。
 * Blogger APIV3には使用制限があるので、APIから１日１回だけデータを読み取って
 * GAEのData Storeへ転送し、通常はここからデータを送信する。
 * 
 * @author LeoPanda
 *
 */
public class Servlet extends HttpServlet {
		/**
		 * isTest = trueにセットすると
		 * Blogger APIから１ページ分だけしか読み込まない
		 */
	 	final boolean isTest = false;

	 	@Override
	 	/*
	 	 * DoGet
	 	 * http://(デプロイサーバー)/test
	 	 * パラメータ:
	 	 * callback:JSONPコールバック関数を指定
	 	 * reload: (true) パラメータがあればBlogger APIからデータを取得しなおす
	 	 * resetall: (true) パラメータがあればデータストアの全情報を削除する 
	 	 */
		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
	 		//パラメータの取得
	 		String reload 	= req.getParameter("reload");
	 		String resetall = req.getParameter("resetall");
			String callBack = req.getParameter("callback");

	 		BloggerService blogService = new BloggerService();
	 		DataStoreHandler dataStoreHandler = new DataStoreHandler();
	 		List<ResultLocation> locations = new  ArrayList<ResultLocation>();
	 		String JSON  = "application/json;charset=utf-8";
	 		String JSONP = "text/javascript;charset=utf-8";

	 		boolean isTriggerd = false;   //データストアの入れ替え指示 
	 		/*
	 		 * パラメータによりリロード指示された場合、
	 		 * Blogger APIを読み直しデータストアを入れ替える。
	 		 * 
	 		 * ただし、１日に１度しか実行できない。
	 		 * 
	 		 * 通常はcronによって自動実行される。
	 		 */
	 		if(reload != null){
		 		isTriggerd = dataStoreHandler.checkTriggerd(); //当日中の実行実績をチェック
	 		}
	 		if(isTriggerd){
				locations = blogService.getLocationList(isTest);
			}else{
				locations =	dataStoreHandler.getFromDataStore();
			} 		
	 		//応答データを作成
			String responseJsonBody = blogService.getLocationJson(locations);

			//データストアの書き込み前にレスポンスを送信
			if(callBack != null){
				resp.setContentType(JSONP);
				resp.getWriter().print(callBack + "(" + responseJsonBody + ");");
			}else{
				resp.setContentType(JSON);
				resp.getWriter().print(responseJsonBody);				
			}
			
			//データストアへの書き込み
			if(isTriggerd){
				dataStoreHandler.setToDataStore(locations);
			}
			//データストアの全情報をクリアするオプション
	 		if(resetall != null){
	 			dataStoreHandler.removeEntity(Entities.LOCATION.getName());
	 			dataStoreHandler.removeEntity(Entities.TRIGGER.getName());
	 			dataStoreHandler.removeEntity(Entities.OLD_TRIGGER.getName());
	 			dataStoreHandler.removeEntity(Entities.OLD_LOCATION.getName());
	 			dataStoreHandler.removeEntity(Entities.OLD_LABEL.getName());
	 		}

	 	}
	 	
		public void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
		}
}
