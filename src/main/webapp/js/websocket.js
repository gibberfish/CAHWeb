
function connectWebsocket (name) {
    var socket = new SockJS('/gameserver');
    
    stompClient = Stomp.over(socket);
    stompClient.connect({"name" : name}, function(frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/gamestate/gameStateUpdates', function(gameStateActionResponse){
            var gameStateActionResponseObject = JSON.parse(gameStateActionResponse.body);
            console.log('Subscribed: ' + gameStateActionResponse.body);
            showGameStateChange(gameStateActionResponseObject.player, gameStateActionResponseObject.command, gameStateActionResponseObject.value, gameStateActionResponseObject.gameId);
        });
        
        sendCommandToGame("login", name);
    });
    $("#connectButton").attr("disabled", true);
    $("#disconnectButton").removeAttr("disabled");
    $("#commandButton").removeAttr("disabled");
}

function disconnectWebsocket () {
	var name = $("#name").val();
    if (stompClient != null) {
    	sendCommandToGame("logout", name);
        stompClient.disconnect();
    }
    $("#connectButton").removeAttr("disabled");
    $("#disconnectButton").attr("disabled", true);
    $("#commandButton").attr("disabled", true);
    console.log("Disconnected");
}
        
function showGameStateChange(player, command, value, game) {
	var result = "I have received a message from " + player + ". Command = " + command + ", value = " + value + ", game = " + game;
	$("#messages").append ("<p>"+result+"</p>");
}

function sendCommandToGameFromScreen() {
    var command = $("#command").val();
    var value = $("#value").val();
    sendCommandToGame(command, value);
}

function sendCommandToGame(command, value) {
    stompClient.send("/cah/gameserver", {}, JSON.stringify({ 'action': command, 'value': value }));
}
