/**
 * 地図表示コントロール用共通ファンクション
 */
    	//デフォルト値
		currentInfoWindow = null; //今開いている情報ウィンドウ
    	//地図描画
    	function setMap(center,zoom){
			var myOptions = {
	    		zoom: zoom,                                    
	    		minZoom: zoom,
	    		center: center,                            
	    		mapTypeControl: false,
	    		panControl:false,
	    		styles: [{featureType:"all"},{
	    				  	stylers: [
		                     {saturation:-72},
		                     {weight:1.1},
		                     {gamma:0.69},
		                     {lightness:26},
		                     {visibility:"simplified"}
		                   ]
	    				}],
	    		mapTypeId: google.maps.MapTypeId.ROADMAP    
			 }
			return new google.maps.Map( document.getElementById( "map-canvas"), myOptions);
    	}
		//マーカー描画
		function setMarker(lat,lng,icon,map){
			var marker = new google.maps.Marker( {
	            position: new google.maps.LatLng( lat, lng),
	            icon:icon ,
	            map: map
	        });
			return marker;
		}
        //マーカーをクリックすると開くウィンドウの設定
		function setClickWindow(locData,marker,i,map){
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
		}
    	//マーカー作成とMarkerCluster配列の作成
    	function setMarkers(locData,map,targetLabel){
    		var icon = setIcon(targetLabel);
		    var mcs = [];
		    var marker, i;
	    	if(targetLabel==''){
			    for (i = 0; i < locData.length; i++) {
			        var marker = setMarker(locData[i].lat,locData[i].lng,icon);		
			    	var infowindow = setClickWindow(locData,marker,i,map);
				    mcs.push( marker);
			    };
		    }else{
			    for (i = 0; i < locData.length; i++) {
		    		if(locData[i].labels.length > 0){
				    	for (j = 0; j < locData[i].labels.length; j++){
				    		  if(locData[i].labels[j] == targetLabel){
							        var marker = setMarker(locData[i].lat,locData[i].lng,icon);				    			  
							    	var infowindow = setClickWindow(locData,marker,i,map);
								    mcs.push( marker);
				    		}				    	
				    	}
		    		}
			    }		    
		    }  
		    return mcs;
    	}
		//常表示infoWindowの設定
		function setInfoWindows(locData,map,targetLabel,omitString){
			for(i = 0; i < locData.length; i++){
	    		if(locData[i].labels.length > 0){
			    	for (j = 0; j < locData[i].labels.length; j++){
			    		if(locData[i].labels[j] == targetLabel){
							var infoWindow = new google.maps.InfoWindow( {
					            position: new google.maps.LatLng( locData[i].lat, locData[i].lng),
					            content: 
					                '<div class="infoWindow">' +
					                '<a href="' + locData[i].url + '" TARGET="_blank">' 
					                + locData[i].name.replace(omitString,"") + '</a>' +
					                '</div>'
							});
							infoWindow.open(map);
			    		}
			    	}
	    		}
			}
		}
		//アイコンの設定
		function setIcon(label){
			var icon;
			for(i = 0;i < icons.length;i++){
				if(icons[i][0] == label){
					icon = icons[i][1];
					return icon;
				}
			}
		}
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
		}
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
		}

		//ズームを戻す
		function resetZoom(){
			map.setCenter(defaultCenter);
			map.setZoom(defaultZoom);			
		}
		//カテゴリ変更時処理
		function onCategoryChange(selector){
			resetZoom();
			markerCluster.clearMarkers();
			var mcs = setMarkers(LOCATION_DATA,map,selector.options[selector.selectedIndex].value);
		    markerCluster.addMarkers(mcs);// MarkerClusterを表示
		}
		//URLからオプションパラメータを取得する
		function getParm(){
			var urlparm = location.search;
			var parm;
			if( urlparm != null){
				parm = urlparm.slice(urlparm.indexOf('=')+1,urlparm.length);
			}else{
				parm = '';
			}
			return decodeURIComponent(parm);
		}
