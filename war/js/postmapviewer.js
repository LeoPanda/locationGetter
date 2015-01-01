/**
 * JSONPで提供された投稿データ(markers[])を元に地図上に位置マーカーを表示する
 */
    	//JSONP受信ファンクション
    	viewMarkers = function(locData){
    		var selectedCategory = getParm();
    		LOCATION_DATA = locData;//地図データをグローバル化
    		selector = setSelectorIE(selectedCategory);
  	    	map = setMap(defaultCenter,defaultZoom);//地図初期表示
  	    	var mcs = setMarkers(locData,map,selectedCategory);//マーカーの配列
		    markerCluster = new MarkerClusterer( map, mcs, mcOptions );// MarkerClusterを表示
   		};



