
var Cookie = require('../common/cookie.js');
var Websocket = require('../websocket/websocket.js');
var name = "";

$(function() {
	name = Cookie.readCookie ("name");
	Websocket.connectWebsocket(name, handleWebsocketResponseForCahPage);	
	
	
	
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



/* **************************** PAGE-SPECIFIC WEBSOCKET RESPONSE HANDLING ******************************* */
function handleWebsocketResponseForCahPage (gameStateActionResponseObject) {
	var player = gameStateActionResponseObject.player,
    command = gameStateActionResponseObject.command,
    game = gameStateActionResponseObject.game
    
    players = game.players;
	console.log("Players: " + JSON.stringify(players));
	
	for (var i in players) {
		var playerName = players[i].name;
		if (players[i].name == name) {
			playerName += " (me)";
		}
		displayPlayer(i, playerName);
	}

	if (game.state == 'NEW') {
		// Show the modal
		$('#ready-modal').modal({
			show: true,
			backdrop: 'static',
			keyboard: false
		});
	}
	
    var result = "I have received a message from " + player + ". Command = " + command + ", game = " + game;
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
