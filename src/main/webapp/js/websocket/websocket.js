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
