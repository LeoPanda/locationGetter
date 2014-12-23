/**
 * JSONPで提供された投稿データ(markers[])を元に地図上に位置マーカーを表示する
 */
    	viewMarkers = function(markers){
    	
  	    	var latlng = new google.maps.LatLng(35.052763,135.352651,17);
  	    	//地図初期表示
    		var myOptions = {
        		zoom: 6,                                    
        		minZoom: 6,
        		center: latlng,                            
        		mapTypeControl: false,
        		panControl:false,
        		mapTypeId: google.maps.MapTypeId.ROADMAP    
   			 };
    		var map = new google.maps.Map( document.getElementById( "map-canvas"), myOptions);

    		//マーカー作成とMarkerCluster配列への挿入
		    var infowindow = new google.maps.InfoWindow();		
		    var mcs = []; //MarkerCluster用配列		
		    var marker, i;
		    for (i = 0; i < markers.length; i++) {
		        marker = new google.maps.Marker( {
		            position: new google.maps.LatLng( markers[i].lat, markers[i].lng),
		            icon:'footprints.png' ,
		            map: map
		        });
		        //InfoWindow設定
		        google.maps.event.addListener( marker, 'click', ( function( marker, i) {
		            return function() {
		                infowindow.setContent(
		                '<div class="infoWindow">' +
		                '<a href="' + markers[i].url + '" TARGET="_blank">' + markers[i].name + '<br/>' +
		                '<img class="infoImage" src="' + markers[i].imgUrl + '"/></a>' +
		                '</div>'
		                 );
		                infowindow.open( map, marker);
		            }
		        })( marker, i));
		        mcs.push( marker);
		    }
		    //MarkerClusterオプション
		    var mcOptions = { gridSize: 50, maxZoom: 15};
		
		    // MarkerClusterを表示
		    var markerCluster = new MarkerClusterer( map, mcs, mcOptions );
   		}
