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
	
	if (name == player) {
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
