(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var Cookie = require('../common/cookie.js');
var Websocket = require('../websocket/websocket.js');

$(function() {
	Websocket.connectWebsocket(name);
	
	$(".my").mouseenter(mouseOverCardInMyHand);
	$(".my").mouseleave(mouseLeavesCardInMyHand);
	
	$(".btn").click(popupModal);
});

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

},{"../common/cookie.js":2,"../websocket/websocket.js":3}],2:[function(require,module,exports){

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

},{}],3:[function(require,module,exports){
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
}

module.exports.disconnectWebsocket = function (name) {
    if (stompClient != null) {
    	sendCommandToGame("logout", name);
        stompClient.disconnect();
    }
    console.log("WEBSOCKET Disconnected");
}
        
var handleGameStateChange = function(player, command, value, game) {
	var gameId, gameType, gamePage;
	if (game != undefined) {
		gameId = game.gameId;
		gameType = game.gameType.type;
		gamePage = game.gameType.gamePage;
	} else {
		gamePage = "index.html";
	}
	
	ensureWeAreOnTheCorrectPage(gamePage);
	
	var result = "I have received a message from " + player + ". Command = " + command + ", value = " + value + ", game = " + gameId + " of type " + gameType;
	console.log("WEBSOCKET Messsage: " + result);
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
