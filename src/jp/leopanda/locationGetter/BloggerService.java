package jp.leopanda.locationGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;
import jp.leopanda.locationGetter.UrlService.ContentType;
import jp.leopanda.locationGetter.UrlService.Result;
import jp.loepanda.locationGetter.POJO.BloggerPostItem;
import jp.loepanda.locationGetter.POJO.BloggerPostList;
import jp.loepanda.locationGetter.POJO.ResultLocation;
/**
 * Blogger API ハンドリングサービス
 * @author LeoPanda
 *
 */
public class BloggerService {
	private static String API_KEY = ""; //enter your own API_KEY
	private static String BLOG_ID = ""; //enter your own BLOG_ID 
	private static String FEALD_ITEMS = "items(images%2Clabels%2Clocation%2Ctitle%2Curl)%2CnextPageToken";

	private static int blogger_max_results = 10;
	
	private static final Logger log = Logger.getLogger(Servlet.class.getName());

	/**
	 * Blogger投稿のgeoTag情報をJSON形式で取り出す。
	 * @return
	 */
	public String getLocationJson(List<ResultLocation> location) {
		String retJson = null;
		try {
			retJson =  JSON.encode(location);
		} catch (JSONException e) {
			log.info("JsonException occured while encoding.");
			e.printStackTrace();
		}
		return retJson;
	}
	
	
	/**
	 * Blogger投稿全件のうち、geoTagを持つ投稿から必要な地図情報を取り出し
	 * 配列として返す
	 * @return 地図情報のリスト
	 * @throws HostGateException
	 */

	public List<ResultLocation> getLocationList(boolean isTest) {
		List<ResultLocation> resultLocationList = new ArrayList<ResultLocation>();
		BloggerPostList onePage;
		String nextPageToken = "";
		while (nextPageToken != null) {
			onePage = getPostOnePage(nextPageToken);
			if(onePage.getItems() != null){
				for (BloggerPostItem items : onePage.getItems()) {
					if ( items.getLocation() != null ){
						resultLocationList.add(setResultLocation(items));
					}
				}
				nextPageToken = isTest ? null : onePage.getNextPageToken();
			} else {
				nextPageToken = null;
			}
		}
		return resultLocationList;
	}

	/**
	 * 結果データのアイテムをセット
	 * @param items
	 * @return
	 */
	private ResultLocation setResultLocation(BloggerPostItem items) {
		ResultLocation resultLocation = new ResultLocation();
		resultLocation.setId(items.getId());
		resultLocation.setName(items.getTitle());
		resultLocation.setUrl(items.getUrl());
		resultLocation.setLng(items.getLocation().getLng());
		resultLocation.setLat(items.getLocation().getLat());
		if(items.getImages().length>0){
			resultLocation.setImgUrl(items.getImages()[0].getUrl()
										.replaceFirst("/s\\d+/","/s120/"));
		}
		resultLocation.setLabels(Arrays.asList(items.getLabels()));
		return resultLocation;
	}

	/**
	 * blogger_max_resultsで指定した数（最大１０）の
	 * Blogger投稿を配列として取得する
	 * @param pageToken
	 * @return BloggerPostList １ページ分のBlogger投稿
	 * @throws HostGateException
	 */
	private BloggerPostList getPostOnePage(String pageToken){
		String urlStr 	= "https://www.googleapis.com/blogger/v3/blogs/" + BLOG_ID 
				+ "/posts?fields=" + FEALD_ITEMS 
				+ "&maxResults=" + blogger_max_results
		        + (pageToken.length() > 0 ? "&pageToken=" + pageToken : "")
		        +  "&fetchImages=true&key=" + API_KEY;
 
		UrlService urlService = new UrlService();		
		Map<Result,String> results = 
			urlService.fetchGet(urlStr, 
							urlService.setHeader(ContentType.JSON));
		
		if(Integer.valueOf(results.get(Result.RETCODE)) != 200 ) {
			log.info("getBloggerPageError code=" + Result.RETCODE + ":"+ results.get(Result.BODY));
		}
		BloggerPostList postList = 
				JSON.decode(results.get(Result.BODY),BloggerPostList.class);		
		return postList;	
	}
}
