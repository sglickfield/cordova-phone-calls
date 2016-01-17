function PhoneStateListener() {
}

// Sarah Glickfield: TODO: Need to modify this file.  It was copied / pasted


PhoneStateListener.prototype.listen = function (variableName, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "PhoneStateListener", "listen", [variableName]);
};
/*
CallLog.prototype.contact = function (phoneNumber, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "CallLog", "contact", [phoneNumber]);
};

CallLog.prototype.show = function (phoneNumber, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "CallLog", "show", [phoneNumber]);
};

CallLog.prototype.delete = function (id, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "CallLog", "delete", [id]);
};
*/

PhoneStateListener.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.phonestatelistener = new PhoneStateListener();
  return window.plugins.phonestatelistener;
};

cordova.addConstructor(PhoneStateListener.install);