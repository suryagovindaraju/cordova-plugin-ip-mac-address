/*global cordova, module*/
module.exports = {
    request: function (action, message, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "AddressImpl", action, [message]);
    }
};
