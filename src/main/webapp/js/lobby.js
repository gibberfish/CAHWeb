
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
		
		ajaxGetGameTypes(retrieveGameTypesAndDisplay);
	}
});

/* **************************** PAGE PANEL MANIPULATION ******************************* */

function retrieveGameTypesAndDisplay (data) {
	// Will receive an array of game types
	for (var index in data) {
		var gameType = data[index];
		var gameTypesPanel = newGamePanel(index, gameType);
	
		$("#accordion").append(gameTypesPanel);
		
		// Behaviour for expanding a game type panel...
		$('#collapse'+index).on('shown.bs.collapse', function () {
			populatePanelWhenCollapsed($(this));
		}); 
	}
}

function populatePanelWhenCollapsed (collapsiblePanel) {
	var gameType = collapsiblePanel.find(".panel-body").attr("data-game");
	console.log("Collapsing panel for " + gameType);

	// reset the panel	
	var gamePanel = $("div[data-game='"+gameType+"'] > .gameList");
	gamePanel.html("");
	
	ajaxGetGamesForType(gameType, displayGamesForType);
	newGameButton(gameType);
}

function displayGamesForType (data) {
	// Will receive an array of games
	for (var i in data) {
		displayExistingGame(data[i]);
	}
}

function newPlayerAddedToGame () {
	console.log("Added New Player to New Game");
	sendCommandToGame("createGame", name);
}

function newPlayerAddedToExistingGame () {
	console.log("Added New Player to Existing Game");
	sendCommandToGame("joinGame", name);
}
/* **************************** DIRECT HTML GENERATION ******************************* */

function newGameButton(gameType) {
	var newGamePanel = $("div[data-game='"+gameType+"'] > .newGamePanel");
	
	newGamePanel.html("<div class='panel'><button type='button' class='btn btn-default'>New Game</button></div>");

	newGamePanel.find(".btn").click (function () {
		addPlayerToNewGameOfType (gameType, newPlayerAddedToGame);
	});
}

function displayExistingGame (game) {
	console.log("displayGamesForType [data:" + JSON.stringify(game) + "]");
	
	var gameType = game.gameType.type;
	var gameId = game.gameId;
	var players = game.players;
	var gamePanel = $("div[data-game='"+gameType+"'] > .gameList");
	
	var html =
		"<div class='panel'>" +
		"<label for='btn_"+gameId+"' class='col-sm-10 control-label'>" +
		"Game "+gameId+" (";
	
	for (i in players) {
		html += players[i].name;
		if (i != players.length-1) {
			html += ", ";
		}
	}
	
	html +=	")</label><div class='col-sm-2'><button type='button' class='btn btn-primary'>Join</button></div>" +
		"</div>";
	
	gamePanel.append(html);
	
	gamePanel.find(".btn").click (function () {
		addPlayerToExistingGame(gameId, newPlayerAddedToExistingGame);
	});
}

function newGamePanel (index, gameType) {
	var output =
		'<div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title">' +
//		'<a id="'+gameType.type+'" class="collapsed" data-toggle="collapse" data-target="#collapse'+index+'" href="#collapse'+index+'">' +
		'<a class="collapsed" data-toggle="collapse" data-target="#collapse'+index+'" href="#">' +
		gameType.displayName +
		'</a></h4></div>' +
		'<div id="collapse'+index+'" class="panel-collapse collapse">' +
		'<div data-game="' + gameType.type + '" class="panel-body">' +
		'<div class="gameList"></div>' +
		'<div class="newGamePanel"></div>' +
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


/* **************************** COOKIE HANDLING ******************************* */

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

/* **************************** AJAX CALLS ******************************* */

function ajaxGetGameTypes (successFunction) {
	$.ajax({
		url: "/game/type/getAll",
		success: successFunction
	});
}

function ajaxGetGamesForType (gameType, successFunction) {
	$.ajax({
		url: "/games/getForType",
		data: {gameType: gameType},
		success: successFunction
	});
}

function addPlayerToNewGameOfType (gameType, successFunction) {
	$.post({
		url: "/game/addPlayerToNewGame",
		data: {gameType: gameType, player: name},
		success: successFunction
	});
}

function addPlayerToExistingGame (gameId, successFunction) {
	$.post({
		url: "/game/addPlayerToExistingGame",
		data: {gameId: gameId, player: name},
		success: successFunction
	});
}
