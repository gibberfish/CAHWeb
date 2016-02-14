
function connectWebsocket (name) {
    var socket = new SockJS('/gameserver');
    
    stompClient = Stomp.over(socket);
    stompClient.connect({"name" : name}, function(frame) {
        console.log('Connected: ' + frame);
        
        sendCommandToGame("login", name);
        
        stompClient.subscribe('/gamestate/gameStateUpdates', function(gameStateActionResponse){
            var gameStateActionResponseObject = JSON.parse(gameStateActionResponse.body);
            showGameStateChange(gameStateActionResponseObject.player, gameStateActionResponseObject.command, gameStateActionResponseObject.value);
        });
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
        
function showGameStateChange(player, command, value) {
	var result = "I have received a message from " + player + ". Command = " + command + ", value = " + value;
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
