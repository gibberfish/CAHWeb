
module.exports.getGameTypes = function (successFunction) {
	$.ajax({
		url: "/game/type/getAll",
		success: successFunction
	});
}

module.exports.getGamesForType = function (gameType, successFunction) {
	$.ajax({
		url: "/games/getForType",
		data: {gameType: gameType},
		success: successFunction
	});
}

module.exports.addPlayerToNewGameOfType = function (gameType, name, successFunction) {
	$.post({
		url: "/game/addPlayerToNewGame",
		data: {gameType: gameType, player: name},
		success: successFunction
	});
}

module.exports.addPlayerToExistingGame = function (gameId, name, successFunction) {
	$.post({
		url: "/game/addPlayerToExistingGame",
		data: {gameId: gameId, player: name},
		success: successFunction
	});
}
