(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var Cookie = require('../common/cookie.js');
var Websocket = require('../websocket/websocket.js');
var Ajax = require('../cah/cahAjax.js');
var name = "";

$(function() {
	name = Cookie.readCookie ("name");
	Websocket.connectWebsocket(name, handleWebsocketResponseForCahPage);	
	
	$("#ready").click (function () {
		Ajax.playerReady(name, playerIsReady);		
		//TODO OR SHOULD WE USE A WEBSOCKET ACTION HERE???
	});
	
	
	$(".my").mouseenter(mouseOverCardInMyHand);
	$(".my").mouseleave(mouseLeavesCardInMyHand);
	
	$(".btn").click(popupModal);
});

function playerIsReady () {
	console.log("Player ready");
	$('#ready-modal').modal({
		show: false,
		backdrop: false,
		keyboard: true
	});
}



function mouseOverCardInMyHand () {
	$(this).removeClass("in-background");
};

function mouseLeavesCardInMyHand () {
	$(".my").addClass("in-background");
};

// See http://www.ericmmartin.com/projects/simplemodal/#options
function popupModal () {
	$("#popup").modal({overlayClose:true, onOpen: slowOpenDialog});

	setTimeout(function () {
          $.modal.close();
    }, 5000);

	return false;
};

function slowOpenDialog (dialog) {
	dialog.overlay.fadeIn('slow', function () {
			dialog.container.slideDown('slow', function () {
				dialog.data.fadeIn('slow');
			});
		});		
};



/* **************************** PAGE-SPECIFIC WEBSOCKET RESPONSE HANDLING ******************************* */
function handleWebsocketResponseForCahPage (gameStateActionResponseObject) {
	var playerPerformingAction = gameStateActionResponseObject.player,
    	command = gameStateActionResponseObject.command,
    	game = gameStateActionResponseObject.game;
	
    var	players = game.players;
    var thisPlayer;
	
	console.log("Players: " + JSON.stringify(players));
	
	for (var i in players) {
		var playerName = players[i].name;
		if (players[i].name == name) {
			playerName += " (me)";
			thisPlayer = players[i];
		}
		displayPlayer(i, playerName);
	}

	if (game.gameState == 'NEW' && thisPlayer.playerState == 'JOINED') {
		// Show the modal
		$('#ready-modal').modal({
			show: true,
			backdrop: 'static',
			keyboard: false
		});
	}
	
    var result = "I have received a message from " + playerPerformingAction + ". Command = " + command + ", game = " + game;
	console.log("WEBSOCKET Messsage on CAH Page: " + result);
}


/* **************************** HTML MANIPULATION ******************************* */
function displayPlayer (index, player) {
	console.log("displayPlayer: " + index + " : " + player);
	var playerTab = $("#player"+index);
	
	var output = '<div class="personName">'+player+'</div>';
	output += '<img class="person-img"/>';
	output += '<div class="miniCard white">Cards<br>Against<br>Humanity</div>';
	output += '<div class="miniCard black"><div>Cards<br>Against<br>Humanity</div><div class="won-cards">0</div></div>';
	
	playerTab.html(output);
	playerTab.removeClass('inactive');
	playerTab.addClass('active');
}

},{"../cah/cahAjax.js":2,"../common/cookie.js":3,"../websocket/websocket.js":4}],2:[function(require,module,exports){

module.exports.playerReady = function (name, successFunction) {
	$.post({
		url: "/player/ready",
		data: {player: name},
		success: successFunction
	});
}

},{}],3:[function(require,module,exports){

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

},{}],4:[function(require,module,exports){
var socket = new SockJS('/gameserver');
var stompClient = Stomp.over(socket);

module.exports.connectWebsocket = function (name, functionToCallOnResponse) {
    stompClient.connect({"name" : name}, function(frame) {
        console.log('WEBSOCKET Connected: ' + frame);
        
        stompClient.subscribe('/gamestate/gameStateUpdates', function(gameStateActionResponse){
            var gameStateActionResponseObject = JSON.parse(gameStateActionResponse.body);
            handleGameStateChange(name, gameStateActionResponseObject, functionToCallOnResponse);
        });

        // Immediately send a connected action so that we get a response as to which game we may already be in
        sendCommandToGame("login", name);
    });
}

module.exports.disconnectWebsocket = function (name) {
    if (stompClient != null) {
    	sendCommandToGame("logout", name);
        stompClient.disconnect();
    }
    console.log("WEBSOCKET Disconnected");
}
        
var handleGameStateChange = function(name, gameStateActionResponseObject, functionToCallOnResponse) {
	var player = gameStateActionResponseObject.player,
	    command = gameStateActionResponseObject.command,
	    game = gameStateActionResponseObject.game
	
	var gameId, gameType, gamePage;
	if (game != undefined) {
		gameId = game.gameId;
		gameType = game.gameType.type;
		gamePage = game.gameType.gamePage;
	} else {
		gamePage = "index.html";
	}
	
	if (name == player.name) {
		ensureWeAreOnTheCorrectPage(gamePage);
	}
	
	functionToCallOnResponse (gameStateActionResponseObject);
}
module.exports.handleGameStateChange = handleGameStateChange;

var sendCommandToGame = function(command, value) {
    stompClient.send("/cah/gameserver", {}, JSON.stringify({ 'action': command, 'value': value }));
}
module.exports.sendCommandToGame = sendCommandToGame;

//Private
var ensureWeAreOnTheCorrectPage = function (page) {
	var pathName = location.pathname.substring(location.pathname.indexOf('/') + 1);
	if (page != pathName) {
		window.location.replace("/" + page);
	}	
}

},{}]},{},[1]);
