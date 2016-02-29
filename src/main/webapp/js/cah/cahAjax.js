
module.exports.playerReady = function (name, successFunction) {
	$.post({
		url: "/player/ready",
		data: {player: name},
		success: successFunction
	});
}
