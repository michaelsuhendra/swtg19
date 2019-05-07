<!DOCTYPE HTML>
<html lang='de' dir='ltr'>
<head>
	<meta charset="utf-8" />
	<title>Smart Mobility - ${pagetitle}</title>
	<link type="text/css" href="css/style.css" rel="stylesheet" media="screen" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
  	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  	<script>
  		$(function() {
    		$( "#datepicker2" ).datepicker(
    		{
    			minDate:'today',
    			
    		});
 
  			$("#datepicker1").datepicker({
  				minDate:'today',
    			onSelect: function (dateValue, inst) {
        			$("#datepicker2").datepicker("option", "minDate", dateValue)
    			}
			});
		});
  	</script>
  	<style>

  	th{
  	text-align:left;
  	background: #ffffff; 
  	}
  	
  	table,tr,th,td{
  	border: none ;
  	}
  	
  	fieldset{
  	border: 0px;
  	}
 

  	</style>
</head>
<body>
<div id="wrapper">
	<div id="logo">Smart Mobility<br>Sharing is Caring</div>
    <ul id="navigation">
    	<li><a href="index" title="Index">Home</a></li>
	<#if navtype == "Passenger">
    	<li><a href="passengergui?page=defaultwebpageP" title="Search Offers">Search Offers</a></li>	
	<#elseif navtype == "Driver">
		<li><a href="drivergui?page=insertoffer" title="Create Offer">Create Offer</a></li>
	<#else>
    	<li><a href="drivergui" title="Driver">Driver</a></li>
		<li><a href="passengergui" title="Passenger">Passenger</a></li>
	</#if>
    </ul>
	<div id="content">