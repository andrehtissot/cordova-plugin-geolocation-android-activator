var exec = require('cordova/exec');
module.exports = {
  askActivation: function(success, error){
    exec(success, error, 'LocationActivation', 'askActivation', []);
  }
};