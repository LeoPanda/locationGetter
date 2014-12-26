/**
 * JSONPで提供された投稿データ(markers[])を元に地図上に位置マーカーを表示する
 */
    	//デフォルト値
		defaultCenter = new google.maps.LatLng(35.052763,135.352651,17);
    	defaultZoom = 6;
		mcOptions = { gridSize: 50, maxZoom: 15};//MarkerClusterオプション	
		currentInfoWindow = null; //今開いている情報ウィンドウ
		icons = [
		         ["","/image/footprints.png","すべて"],
		         ["絶景","/image/camera.png","絶景ポイント"],
		         ["花","/image/cherry.png","花風景ポイント"],
		         ["紅葉","/image/leaf.png","紅葉ポイント"],
		         ["ルート図","/image/bike.png","自転車ルート"]
				];
    	//地図描画
    	function setMap(center,zoom){
			var myOptions = {
	    		zoom: zoom,                                    
	    		minZoom: zoom,
	    		center: center,                            
	    		mapTypeControl: false,
	    		panControl:false,
	    		mapTypeId: google.maps.MapTypeId.ROADMAP    
			 };
			return new google.maps.Map( document.getElementById( "map-canvas"), myOptions);
    	};
		//マーカー描画
		function setMarker(lat,lng,icon,map){
			var marker = new google.maps.Marker( {
	            position: new google.maps.LatLng( lat, lng),
	            icon:icon ,
	            map: map
	        });
			return marker;
		};
        //InfoWindow設定
		function setInfoWindow(locData,marker,i,map){
		    var infowindow = new google.maps.InfoWindow();		
	        google.maps.event.addListener( marker, 'click', ( function( marker, i) {
	            return function() {
	                infowindow.setContent(
	                '<div class="infoWindow">' +
	                '<a href="' + locData[i].url + '" TARGET="_blank">' + locData[i].name + '<br/>' +
	                '<img class="infoImage" src="' + locData[i].imgUrl + '"/></a>' +
	                '</div>'
	                 );
	                if(currentInfoWindow){
	                	currentInfoWindow.close();
	                }
	                infowindow.open( map, marker);
	                currentInfoWindow = infowindow; //開いているインフォウィンドウを常にひとつにする
	            };
	        })( marker, i));
	        return infowindow;
		};
    	//マーカー作成とMarkerCluster配列の作成
    	function setMarkers(locData,map,targetLabel){
    		var icon = setIcon(targetLabel);
		    var mcs = [];
		    var marker, i;
	    	if(targetLabel==''){
			    for (i = 0; i < locData.length; i++) {
			        var marker = setMarker(locData[i].lat,locData[i].lng,icon);		
			    	var infowindow = setInfoWindow(locData,marker,i,map);
				    mcs.push( marker);
			    };
		    }else{
			    for (i = 0; i < locData.length; i++) {
		    		if(locData[i].labels.length > 0){
				    	for (j = 0; j < locData[i].labels.length; j++){
				    		  if(locData[i].labels[j] == targetLabel){
							        var marker = setMarker(locData[i].lat,locData[i].lng,icon);				    			  
							    	var infowindow = setInfoWindow(locData,marker,i,map);
								    mcs.push( marker);
				    		};				    	
				    	};
		    		};
	    		};		    
		    };   
		    return mcs;
    	};
		//アイコンの設定
		function setIcon(label){
			var icon;
			for(i = 0;i < icons.length;i++){
				if(icons[i][0] == label){
					icon = icons[i][1];
					return icon;
				}
			}
		};
		//カテゴリセレクタの描画
		function setSelector(selected){
			var options = '';
			for(i = 0; i < icons.length; i++){
				options += '<option'
					+ ' value="'  + icons[i][0] + '"'
					+ ((icons[i][0] == selected) ? 'selected ' : '')
					+ '>' + icons[i][2] + '</option>';
			}
			var selector = document.getElementById('category-selector')
			selector.innerHTML = options;
			return selector;
		};
		//カテゴリセレクタの描画（IEでinnerHTMLが使えないバグ対応）
   		function setSelectorIE(selected){
			var selector = document.getElementById('category-selector');
			for(i = 0;i<icons.length;i++){
				var op = document.createElement('option');
				op.value = icons[i][0];
				op.appendChild(document.createTextNode(icons[i][2]));
				op.selected = (icons[i][0] == selected) ? true: false;
				selector.appendChild(op);
			}
			return selector;
		};

		//ズームを戻す
		function resetZoom(){
			map.setCenter(defaultCenter);
			map.setZoom(defaultZoom);			
		};
		//カテゴリ変更時処理
		function onCategoryChange(selector){
			resetZoom();
			markerCluster.clearMarkers();
			var mcs = setMarkers(LOCATION_DATA,map,selector.options[selector.selectedIndex].value);
		    markerCluster.addMarkers(mcs);// MarkerClusterを表示
		};
		//オプションパラメータの取得
		function getParm(){
			var urlparm = location.search;
			var parm;
			if( urlparm != null){
				parm = urlparm.slice(urlparm.indexOf('=')+1,urlparm.length);
			}else{
				parm = '';
			}
			return decodeURIComponent(parm);
		};
    	//JSONP受信ファンクション
    	viewMarkers = function(locData){
    		var selectedCategory = getParm();
    		LOCATION_DATA = locData;//地図データをグローバル化
    		selector = setSelectorIE(selectedCategory);
  	    	map = setMap(defaultCenter,defaultZoom);//地図初期表示
  	    	var mcs = setMarkers(locData,map,selectedCategory);//マーカーの配列
		    markerCluster = new MarkerClusterer( map, mcs, mcOptions );// MarkerClusterを表示
   		};



