
$(function() {
	$(".my").mouseenter(mouseOverCardInMyHand);
	$(".my").mouseleave(mouseLeavesCardInMyHand);
	
	$(".btn").click(popupModal);
	
	openSocket();
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
}

function slowOpenDialog (dialog) {
	dialog.overlay.fadeIn('slow', function () {
			dialog.container.slideDown('slow', function () {
				dialog.data.fadeIn('slow');
			});
		});		
}






var webSocket;
var messages = document.getElementById("messages");


function openSocket(){
    // Ensures only one connection is open at a time
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
       writeResponse("WebSocket is already opened.");
        return;
    }
    // Create a new instance of the websocket
    webSocket = new WebSocket("ws://localhost:1970/cahweb/game");
     
    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function(event){
        // For reasons I can't determine, onopen gets called twice
        // and the first time event.data is undefined.
        // Leave a comment if you know the answer.
        if(event.data === undefined)
            return;

        writeResponse(event.data);
    };

    webSocket.onmessage = function(event){
        writeResponse(event.data);
    };

    webSocket.onclose = function(event){
        writeResponse("Connection closed");
    };
}

function login () {
    var name = document.getElementById("name").value;
    var message = {};
    message.command = 'login';
    message.value = name;
    var messageString = JSON.stringify(message);
    webSocket.send(messageString);
}

/**
 * Sends the value of the text input to the server
 */
function send(){
    var text = document.getElementById("messageinput").value;
    webSocket.send(text);
}

function closeSocket(){
    webSocket.close();
}

function writeResponse(text){
    messages.innerHTML += "<br/>" + text;
}