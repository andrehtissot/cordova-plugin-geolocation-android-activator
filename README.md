# cordova-plugin-geolocation-android-activator
Simple complement for geolocation plugins, like apache's cordova-plugin-geolocation, that asks the user to give permission to access geolocation and to activate the GPS.

## To use
#### Just call it
If the user allows or have previously allowed the app to access geolocation and GPS was allowed to be activated or was already active, the success callback will be executed. In any other case, the failure callback will.
```js
navigator.geolocation.activator.askActivation(function(response) {
  //Success callback
}, function(response) {
  //Failure callback
});
```

#### Response object
The `response` parameter will always return a object;
```json
{
  "status": 8,
  "message": "GPS activated"
}
```
All `status` values can be found in `navigator.geolocation.activator.returnStatus` for comparison.

If the GPS is not activated and the app has location permission, the user will be asked to accept it's activation, like the images below.

#### If app doesn't have location permission:
![asking-geolocation-permission](https://cloud.githubusercontent.com/assets/1174345/26087300/516cf27a-39c6-11e7-9d3c-f485fb866f58.png)

#### Initialy as:
![closed GPS activation modal](https://cloud.githubusercontent.com/assets/1174345/26030372/4abeff80-3828-11e7-8b1a-f415551b263e.png)

#### When "∨" is clicked:
![opened GPS activation modal](https://cloud.githubusercontent.com/assets/1174345/26030373/4abf0372-3828-11e7-8be7-fbe1e398facf.png)

