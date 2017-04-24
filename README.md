# Uses of Mapbox API library 

  - Initialize mapview 
  - Directions from source to destination.
  - Create custom marker on the map
  - Draw polyline on the map
# How to use Mapbox API library

#### Copy following files to your project

    1. api, custommarker, directionspojo, services and utils to your src folder.
    2. custommarker.xml to your res folder
    3. ic_current_location and ic_destination_location to your drawable folder.

#### Add following libraries in project build.gradle file
   ```java
1. compile('com.mapbox.mapboxsdk:mapbox-android-sdk:3.2.0@aar') {
        	transitive = true
    	}
   2.   compile 'com.squareup.retrofit:retrofit:1.9.0'
```


####  Add following permissions in AndroidManifest.xml file
```java
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
    
  #### Initialize Mapbox:
   > Initialize mapbox api in ``` onCreate()```  method of in your activity file
   ```java
   MapboxApi mapboxApi = new MapboxApi();
   mapboxApi.initializeMap(Context context, MapView mapView,"your mapbox access token");
   ```
   ##### Directions from Source to Destination:
- Examples Parameters: 
    - sourceMarkerImage = R.drawable.ic_current_location
    - pathColor  = "#FF0000"
    - pathWidth = 4; 
- Syntax :
   ```java
  mapboxApi.setPathAttrubutes(int sourceMarkerImage, int destinationMarkerImage,String pathColor, int pathWidth);
  ```
  
  > Start Directions Service Call: 
  ```java 
    mapboxApi.startDirectionsServiceCall(Context context, MapView mapView, String accessToken,LatLang sourceCoordinates,LatLang destinationCoordinates); 
  ```
  
>  Get duration :-  
  - Get Duration in Seconds : 
 ```java
            mapboxApi.getDurationInSeconds(), 
  ```
            
  - Get duration in Minutes : 
  ```java 
    mapboxApi.getDurationInMinutes() 
```
  - Get Duration in Hours : 
```java 
    mapboxApi.getDurationInHours(); 
```
     
>  Get distance :- 
     
- Get Distance in Meters : 
```java
    mapboxApi.getDistanceInMetres(); 
```
- Get Distance in Kilo meters :
```java
    mapboxApi.getDistanceInKilometres();
 ```
 - Get Distance in Miles :
```java
    mapboxApi.getDistanceInMiles();
```
> Number of stops (or) stops between two places :

- Get Steps (or) Stops : ``` Returns : List<Steps> ```
```java 
    mapboxApi.getStepsBetweenSourceAndDestination();	
```

> Create custom marker on the map:

- Example parameters to be possed into argument
    - markerImageValue = R.drawable.ic_current_location;
	- label = "India"
	- ``` returns Marker ```
```java 
    mapboxApi.createMarker( Context context, MapView mapView, LatLng latlang, String label,int markerImageValue); 
 Note :  Customize your marker using layout named "custommarker.xml"
```
	  
	  
> Draw polyline on the map:
- Example parameters to be possed into argument
    - width = 4;
	- color = "#FF0000"
```java 
    mapboxApi.drawPolyline(Context context, MapView mapView, List<LatLng> pathPointsArray, int width, String color);
```
