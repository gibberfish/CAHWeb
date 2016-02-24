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
