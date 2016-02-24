(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

module.exports.readCookie = function (cookieName) {
	var name = cookieName + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}

module.exports.writeCookie = function (cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

},{}],2:[function(require,module,exports){

var Cookie = require('../common/cookie.js');
var Websocket = require('../websocket/websocket.js');
var Ajax = require('./lobbyAjax.js');

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
		Cookie.writeCookie("name", name, 365);
	});

	name = Cookie.readCookie ("name");

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
	
		Websocket.connectWebsocket(name);
		
		Ajax.getGameTypes(retrieveGameTypesAndDisplay);
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
	
	Ajax.getGamesForType(gameType, displayGamesForType);
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
	Websocket.sendCommandToGame("createGame", name);
}

function newPlayerAddedToExistingGame () {
	console.log("Added New Player to Existing Game");
	Websocket.sendCommandToGame("joinGame", name);
}
/* **************************** DIRECT HTML GENERATION ******************************* */

function newGameButton(gameType) {
	var newGamePanel = $("div[data-game='"+gameType+"'] > .newGamePanel");
	
	newGamePanel.html("<div class='panel'><button type='button' class='btn btn-default'>New Game</button></div>");

	newGamePanel.find(".btn").click (function () {
		Ajax.addPlayerToNewGameOfType (gameType, name, newPlayerAddedToGame);
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
		Ajax.addPlayerToExistingGame(gameId, name, newPlayerAddedToExistingGame);
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


},{"../common/cookie.js":1,"../websocket/websocket.js":4,"./lobbyAjax.js":3}],3:[function(require,module,exports){

module.exports.getGameTypes = function (successFunction) {
	$.ajax({
		url: "/game/type/getAll",
		success: successFunction
	});
}

module.exports.getGamesForType = function (gameType, successFunction) {
	$.ajax({
		url: "/games/getForType",
		data: {gameType: gameType},
		success: successFunction
	});
}

module.exports.addPlayerToNewGameOfType = function (gameType, name, successFunction) {
	$.post({
		url: "/game/addPlayerToNewGame",
		data: {gameType: gameType, player: name},
		success: successFunction
	});
}

module.exports.addPlayerToExistingGame = function (gameId, name, successFunction) {
	$.post({
		url: "/game/addPlayerToExistingGame",
		data: {gameId: gameId, player: name},
		success: successFunction
	});
}

},{}],4:[function(require,module,exports){
var socket = new SockJS('/gameserver');
var stompClient = Stomp.over(socket);

module.exports.connectWebsocket = function (name) {
    stompClient.connect({"name" : name}, function(frame) {
        console.log('WEBSOCKET Connected: ' + frame);
        
        stompClient.subscribe('/gamestate/gameStateUpdates', function(gameStateActionResponse){
            var gameStateActionResponseObject = JSON.parse(gameStateActionResponse.body);
            handleGameStateChange(gameStateActionResponseObject.player, gameStateActionResponseObject.command, gameStateActionResponseObject.value, gameStateActionResponseObject.game);
        });

        // Immediately send a connected action so that we get a response as to which game we may already be in
        sendCommandToGame("login", name);
    });
    //$("#connectButton").attr("disabled", true);
    //$("#disconnectButton").removeAttr("disabled");
    //$("#commandButton").removeAttr("disabled");
}

module.exports.disconnectWebsocket = function (name) {
	//var name = $("#name").val();
    if (stompClient != null) {
    	sendCommandToGame("logout", name);
        stompClient.disconnect();
    }
    //$("#connectButton").removeAttr("disabled");
    //$("#disconnectButton").attr("disabled", true);
    //$("#commandButton").attr("disabled", true);
    console.log("WEBSOCKET Disconnected");
}
        
var handleGameStateChange = function(player, command, value, game) {
	var gameId, gameType;
	if (game != undefined) {
		gameId = game.gameId;
		gameType = game.gameType.type;
		gamePage = game.gameType.gamePage;

		var pathName = location.pathname.substring(location.pathname.indexOf('/') + 1);
		console.log(pathName);
		
		if (gamePage != pathName) {
			window.location.replace("/" + gamePage);
		}
	}
	var result = "I have received a message from " + player + ". Command = " + command + ", value = " + value + ", game = " + gameId + " of type " + gameType;
	console.log("WEBSOCKET Messsage: " + result);
	
	//$("#messages").append ("<p>"+result+"</p>");
}
module.exports.handleGameStateChange = handleGameStateChange;
//TODO Remove - this was only for demo purposes
//function sendCommandToGameFromScreen() {
//    var command = $("#command").val();
//    var value = $("#value").val();
//    sendCommandToGame(command, value);
//}

var sendCommandToGame = function(command, value) {
    stompClient.send("/cah/gameserver", {}, JSON.stringify({ 'action': command, 'value': value }));
}
module.exports.sendCommandToGame = sendCommandToGame;

},{}]},{},[2]);
