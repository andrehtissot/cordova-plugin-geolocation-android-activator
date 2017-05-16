var exec = require('cordova/exec');
module.exports = {
  returnStatus: {
    ALREADY_HAS_ALL_PERMISSIONS: 0,
    CALLBACK_CONTEXT_NOT_FOUND: 1,
    PERMISSION_DENIED: 2,
    PERMISSION_OBTAINED: 3,
    GPS_ALREADY_ACTIVE: 4,
    ERROR_ON_REQUEST_CHECK_SETTINGS: 5,
    GPS_CANNOT_BE_ACTIVATED: 6,
    GPS_ACTIVATION_DENIED: 7,
    GPS_ACTIVATED: 8
  },
  askActivation: function(success, error){
    exec(success, error, 'LocationActivation', 'askActivation', []);
  }
};