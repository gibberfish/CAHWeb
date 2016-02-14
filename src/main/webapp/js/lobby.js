
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
		$('#welcome-back').text("Please identify yourself first...");
	
		// Show the modal
		$('#login-modal').modal({
			show: true,
			backdrop: 'static',
			keyboard: false
		});
	} else {
		$('#welcome-back').text("Welcome back " + name + ". Please choose a game below...");
	
		connectWebsocket(name);
		
		// Get the list of games
		$.ajax({url: "/game/type/getAll"}).then(retrieveGameTypesAndDisplay);
	}
});

function retrieveGameTypesAndDisplay (data) {
	for (var i in data) {
		$("#accordion").append(newGamePanel(i, data[i]));
		
		$('#collapse'+i).on('shown.bs.collapse', function () {
			// Get the list of games for this type
		
		
		
			newGameButton(i, $(this));
			//$(this).html("<div class='panel'><button type='button' class='btn btn-default'>New Game 2</button></div>");
		});
	}
}

function newGamePanel (index, gameType) {

	var output = '<div class="panel panel-default" id="panel1">' +
			'<div class="panel-heading">' +
			'<h4 class="panel-title">' +
			'<a data-toggle="collapse" id="'+gameType.type+'" data-target="#collapse'+index+'" href="#collapse'+index+'" class="collapsed">' +
			gameType.displayName +
			'</a>' +
			'</h4>' +
			'</div>' +
			'<div id="collapse'+index+'" class="panel-collapse collapse">' +
			'<div class="panel-body">' +
//						<div class="panel">
//							Game 1: Anne, Matt
//							<button type="button" class="btn btn-primary">Join</button>
//						</div>
//						<div class="panel">
//							<button type="button" class="btn btn-default">New Game</button>
//						</div>
			'</div></div></div>';

	return output;
};

function newGameButton (index, collapsiblePanel) {
	collapsiblePanel.html("<div class='panel'><button id='newGame"+index+"' type='button' class='btn btn-default'>New Game</button></div>");
	$('#newGame'+index).click (function () {
		// HARD CODED AS TEST FOR NOW
		$.post({url: "/game/addPlayerToNewGame", data: "{gameType: 'fluxx', player: 'Mark'}"}).then(function (data) {
			alert("Added New Player to Game");
		});
	});
}

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
