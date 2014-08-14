<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>A ReSTFUll Order Entry System in Java</title>
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen"/>
</head>
<body>
	<div class="container">

      <div class="masthead">
        <h3 class="muted">Order Entry</h3>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li class="active"><a href="index.jsp">Home</a></li>
                <li><a href="#.jsp" id="customerLink">Customer</a></li>
                <li><a href="#.jsp" id="productLink">Product</a></li>
                <li><a href="#.jsp" id="orderManagementLink">Order Management</a></li>
                <li><a href="#">About</a></li>
                <li><a href="#">Contact</a></li>
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>

      <!-- Jumbotron -->
      <div class="jumbotron">
        <h1>Ordering stuff!</h1>
        <p class="lead">This application is a good example showing how to use REST architecture in Java.</p>
        <a class="btn btn-large btn-success" href="#">Get started today</a>
      </div>

      <hr>

      <!-- Example row of columns -->
      <div class="row-fluid" id="targetDiv">
        this is the target div I am going to use for displaying the jsp files.
      </div>

      <hr>

      <div class="footer">
        <p>&copy; Company 2014</p>
      </div>

    </div> <!-- /container -->
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.1.js"></script>            
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$('#customerLink').click(function(){
			//I want to display an inner menu for customer...CRUD...
			$('#targetDiv').load('showcustomermenubar.jsp');
		});
		
		$('#productLink').click(function(){});
		
		$('#orderManagementLink').click(function(){});
	});//end document.ready function
</script>
</html>