
/* **************************** MAIN PAGE JQUERY ON-LOAD ******************************* */
$(function() {
	$("#popup").modal('show');
	//popupModal();
	//$(".connectButton").click(popupModal);
});

/* **************************** MODAL HANDLING ******************************* */
// See http://www.ericmmartin.com/projects/simplemodal/#options
function popupModal () {
	$("#popup").modal({overlayClose:false, onOpen: slowOpenDialog});

//	setTimeout(function () {
//          $.modal.close();
//    }, 5000);

//	return false;
};

function slowOpenDialog (dialog) {
	dialog.overlay.fadeIn('slow', function () {
			dialog.container.slideDown('slow', function () {
				dialog.data.fadeIn('slow');
			});
		});		
};
