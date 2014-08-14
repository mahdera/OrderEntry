<ul class="nav nav-pills">
	<li><a href="#.jsp" id="registerCustomerLink">Register Customer</a></li>
	<li><a href="#.jsp" id="listCustomers">List Customers</a></li>
	<li><a href="#.jsp" id="editCustomer">Edit Customer</a></li>
	<li><a href="#.jsp" id="deleteCustomer">Delete Customer</a></li>
</ul>
<div id="innerMenuBarDiv"></div>
<script type="text/javascript">
	$(document).ready(function(){
		$('#registerCustomerLink').click(function(){
			$('#innerMenuBarDiv').load('showcustomerregistrationform.jsp');
		});
		
		$('#listCustomers').click(function(){			
			$.ajax({
				url : 'http://localhost:8090/OrderEntry/api/customer/list',
				data : null,
				type : 'GET',
				sucess: function(data1){
					alert('yes');
					//$('#innerMenuBarDiv').load("showlistofcustomers.jsp?jsonArray="+data);
				},
				error : function(error){
					alert('error has occured');
				}
			});
		});
	});//end document.ready function
</script>