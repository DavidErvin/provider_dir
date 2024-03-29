function loadProviders() {
	// get the provider table body
	var providerTable = $("#provider_container");
	
	// figure out if we're sorting
	var dropdown = $("#sort_dropdown");
	var sortSelection = dropdown.val();
	var getProvidersUrl = 'provider';
	if (sortSelection != null) {
		getProvidersUrl += '?sort=' + sortSelection;
	}
	
	// get providers from the REST endpoint
	$.ajax({
		type: 'GET',
		url: getProvidersUrl,
		dataType: 'json'
	}).done(function(json) {
		// clean out any children of the provider table now
		providerTable.empty();
		// not a string, but real live JSON!
		$.each(json, function(i, item) {
			// now I've got an individual provider record
			var HTML = 
				'<div class="container-fluid">' +
					'<div class="row">' +
						'<input class="col-1" type="checkbox" ' +
							'id="provider_check_' + item.id + '" name="' + item.id + '"></input>' +
						'<div class="col-6" align="left">' +
						'<h5>' + item.last_name + ', ' + item.first_name + '</h5>' +
					'</div>' +
					'<div class="col-5" align="right">' +
						'<p><b>' + item.specialty + '</b></p>' +
					'</div>' +
				'</div>' +
				'<div class="row">' +
					'<div class="col-1"></div>' +
						'<div class="col-5">' +
							'<p>' + item.email_address + '</p>' +
						'</div>' +
						'<div class="col-6" align="right">' +
							'<p>' + item.practice_name + '</p>' +
						'</div>' +
					'</div>' +
			'</div>';
			
			var providerContent = $(HTML);
			providerContent.appendTo(providerTable);
		});
	});
	return this;
}

$(document).ready(function() {
	// load providers when the page loads
	loadProviders();
	
	// attach a callback to the search drop down
	var dropdown = $("#sort_dropdown");
	dropdown.on("change", function() {
		loadProviders();
	});
});