/**
 * 
 */
$(document).ready(function() {
	// handle provider submissions
	$('form').submit(function(event) {
		// build the JSON structure we'll send over
		var formData = {
			'first_name' : $('input[name=first_name]').val(),
			'last_name' : $('input[name=last_name]').val(),
			'email_address' : $('input[name=email_address]').val(),
			'specialty' : $('input[name=specialty]').val(),
			'practice_name' : $('input[name=practice_name]').val()
		};

		// POST the form contents to the /provider REST endpoint
		$.ajax({
			type : 'POST',
			url : 'provider',
			data : JSON.stringify(formData),
			contentType : "application/json; charset=utf-8",
			dataType : 'json',
			encode : false
		}).done(function(data) {
			// log data to the console so we can see
			console.log(data);
		});

		// stop the form from submitting the normal way and refreshing the page
		event.preventDefault();

		// clear out the form with plain old JS
		document.getElementById("create_provider").reset();
		
		loadProviders();
	});
});