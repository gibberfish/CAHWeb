
$(function() {
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









function connectWebsocket () {
       var socket = new SockJS('/gameserver');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/gamestate/gameStateUpdates', function(gameStateActionResponse){
                	var gameStateActionResponseObject = JSON.parse(gameStateActionResponse.body);
                    showGameStateChange(gameStateActionResponseObject.player, gameStateActionResponseObject.command, gameStateActionResponseObject.value);
                });
            });
}

function disconnectWebsocket () {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            console.log("Disconnected");
        }
        
function showGameStateChange(player, command, value) {
	var result = "I have received a message from " + player + ". Command = " + command + ", value = " + value;

	$("#messages").append ("<p>"+result+"</p>");

//    var response = document.getElementById('messages');
//            var p = document.createElement('p');
//            p.style.wordWrap = 'break-word';
//            p.appendChild(document.createTextNode(message));
//            response.appendChild(p);
        }

function sendCommandToGame() {
    var command = $("#command").val();
    var value = $("#value").val();
    stompClient.send("/cah/gameserver", {}, JSON.stringify({ 'command': command, 'value': value }));
}
