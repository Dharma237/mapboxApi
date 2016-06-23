1) we can use this component as
	
    i)  Initialize mapview 
    ii) Directions from source to destination.
    iii)Create custom marker on the map
    iV) Draw polyline on the map

2) Copy following files to your project

    i)  api, custommarker, directionspojo, services and utils to your src folder.
    ii) custommarker.xml to your res folder
    iii)ic_current_location and ic_destination_location to your drawable folder.

3) add following libraries in project build.gradle file
    
   i) 	compile('com.mapbox.mapboxsdk:mapbox-android-sdk:3.2.0@aar') {
        	transitive = true
    	}
   ii)  compile 'com.squareup.retrofit:retrofit:1.9.0'

4) Add following permissions in AndroidManifest.xml file
	
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    
5) Use below methods for mapbox apis
	
	 //Initialize mapboxapi in onCreate() method in your activity
	MapboxApi mapboxApi =  new MapboxApi();	

   i ) Initialize map:

	 mapboxApi.initializeMap(context,mapView,"your mapbox access token");
 
   ii) Directions from Source to Destination:

		// Examples : sourceMarkerImage = R.drawable.ic_current_location;
		// pathColor  = "#FF0000"
		// pathWidth = 4;
	 Set Path Attributes: mapboxApi.setPathAttributes(int sourceMarkerImage,int destinationMarkerImage,String pathColor,int 					pathWidth);
	
		//Examples sourceCoordinates =  new LatLng(17.686815, 83.218483);
     Start Directions Service Call:  mapboxApi.startDirectionsServiceCall(context,mapView,String accessToken,LatLang 					sourceCoordinates,LatLang destinationCoordinates);
		
		// returns float value
	   Get duration :- mapboxApi.getDurationInSeconds(), mapboxApi.getDurationInMinutes(), mapboxApi.getDurationInHours();

		// returns float value
     Get distance :- mapboxApi.getDistanceInMetres(), mapboxApi.getDistanceInKilometres(), mapboxApi.getDistanceInMiles();
		
		// List<Steps>
	   Get Steps : mapboxApi.getStepsBetweenSourceAndDestination();
						
 
   iii) Create custom marker on the map:
	
		 // Examples : markerImageValue = R.drawable.ic_current_location;
		 // label = "India"
		 // returns Marker
	  CreateMarker : mapboxApi.createMarker(context, mapView, LatLng latlang, String label,int markerImageValue);

	  Note : Customize your marker using layout named "custommarker.xml"

    iv) Draw polyline on the map:
	
		//Examples : width = 4;
		//color = "#FF0000"
	  Draw Polyline : mapboxApi.drawPolyline(context, mapView, List<LatLng> pathPointsArray, int width, String color);
