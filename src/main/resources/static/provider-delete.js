/**
 * 
 */
function deleteProviders() {
	// get the holster for provider data
	var providerContainer = $("#provider_container");
	
	// get all checkboxes under it
	var checkedBoxes = providerContainer.find("input:checked");
	$.each(checkedBoxes, function(i, check) {
		var deleteUrl = 'provider/' + check.getAttribute("name");
		
		$.ajax({
			type : 'DELETE',
			url : deleteUrl,
			async: false
		}).done(function() {
			// ?
		});
	});
	
	loadProviders();
}


$(document).ready(function() {
	var deleteButton = $("#provider_delete");
	
	deleteButton.on("click", function(event) {
		deleteProviders();
		
		// don't do other button stuff
		event.preventDefault();
	});
});