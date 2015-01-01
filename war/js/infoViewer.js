/**
 * JSONPで提供された投稿データ(markers[])を元に地図上に情報ウィンドウを表示する
 */
    	//JSONP受信ファンクション
    	viewMarkers = function(locData){
    		LOCATION_DATA = locData;//地図データをグローバル化
  	    	map = setMap(defaultCenter,defaultZoom);//地図初期表示
  	    	var mcs = setMarkers(locData,map,Category);//マーカーの配列
//  	    	setInfoWindows(locData,map,Category,OmitString);
		    markerCluster = new MarkerClusterer( map, mcs, mcOptions );// MarkerClusterを表示
   		};



