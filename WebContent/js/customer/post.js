/**
 * js file for post.html
 * Please use modern web browser as this file will not attempt to be
 * compatible with older browsers. Use Chrome and open javascript console
 * or Firefox with developer console.
 * 
 * jquery is required
 */
$(document).ready(function() {
	//console.log("ready");
	$('#firstName').focus();

	var $customerRegistrationForm = $('#customerRegistrationForm');//we have the formObject in this variable....

	/**
	 * This is for the Submit button
	 * It will trigger a ajax POST call to: api/v2/inventory
	 * This will submit a item entry to our inventory database
	 */
	$('#btncustomersave').click(function(e) {
		//console.log("submit button has been clicked");
		e.preventDefault(); //cancel form submit

		var jsObj = $customerRegistrationForm.serializeObject() , ajaxObj = {};

		//console.log(jsObj);

		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8090/OrderEntry/api/customer/save/", 
			data: JSON.stringify(jsObj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
			},
			success: function(data) { 
				$('#innerMenuBarDiv').html( "Customer registered successfully" );				
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
			dataType: "json" //request JSON
		};

		$.ajax(ajaxObj);
	});

});//end document.ready function
 