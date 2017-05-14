# cordova-plugin-geolocation-android-activator
Simple complement for geolocation plugins, like apache's cordova-plugin-geolocation, that asks the user to activate the GPS.


## To use
Just call it.
```js
navigator.geolocation.activator.askActivation();
```

If the GPS is not activated and the app has location permission, the user will be asked to accept it's activation, like the images below.
 
 #### Initialy as:
![closed GPS activation modal](https://cloud.githubusercontent.com/assets/1174345/26030372/4abeff80-3828-11e7-8b1a-f415551b263e.png)

#### When "∨" is clicked:
![opened GPS activation modal](https://cloud.githubusercontent.com/assets/1174345/26030373/4abf0372-3828-11e7-8be7-fbe1e398facf.png)

