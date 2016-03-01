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
        var map = new Object();
        map["name"] = name;
        sendCommandToGame("login", map);
    });
}

module.exports.disconnectWebsocket = function (name) {
    if (stompClient != null) {
        var map = new Object();
        map["name"] = name;
    	sendCommandToGame("logout", map);
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

//TODO Change value to be a map of key-value pairs for the command
var sendCommandToGame = function(command, map) {
    stompClient.send("/cah/gameserver", {}, JSON.stringify({ 'action': command, 'parameters': map }));
}
module.exports.sendCommandToGame = sendCommandToGame;

//Private
var ensureWeAreOnTheCorrectPage = function (page) {
	var pathName = location.pathname.substring(location.pathname.indexOf('/') + 1);
	if (page != pathName) {
		window.location.replace("/" + page);
	}	
}
