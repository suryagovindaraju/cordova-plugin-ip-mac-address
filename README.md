#This plugin is used to get the device IP Address and Mac Address

Install this plugin using 

cordova plugin add cordova-plugin-ip-mac-address 

function call to get the IP Address

	var params = {};
    addressimpl.request("getIPAddress", JSON.stringify(params), function(message) {
        	alert("ip address "+message);
        }, function() {
        	alert("failed on get ip address");
        });

function call to get the Mac Address

	var params = {};
	addressimpl.request("getMACAddress", JSON.stringify(params), function(message) {
        	alert("mac address "+message);
        }, function() {
        	alert("failed on get mac address");
        });