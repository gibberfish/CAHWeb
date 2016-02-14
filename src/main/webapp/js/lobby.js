
var name = "";

/* **************************** MAIN PAGE JQUERY ON-LOAD ******************************* */
$(function() {
	$('#login').click (function () {
		$('#login-modal').modal({
			show: false,
			backdrop: false,
			keyboard: true
		});
		
		name = $('#name').val();
		writeCookie("name", name, 365);
	});

	name = readCookie ("name");

	if (name == "") {
		// Show the modal
		$('#login-modal').modal({
			show: true,
			backdrop: 'static',
			keyboard: false
		});
	} else {
		connectWebsocket(name);
	}
});

function readCookie (cookieName) {
	var name = cookieName + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}

function writeCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
