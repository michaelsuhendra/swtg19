<#include "header.ftl">

<b>Welcome to our car sharing system - Smart Mobility!</b><br><br>

<form method="POST" action="passengergui?action=bookOffer">
	<fieldset id="browseAvailableOffer">
		<legend>Required Information</legend>
	    <div>
	    	<label>Name</label>
	    	<input type="text" name="name">
	    </div>
	    <div>
	    	<label>Email</label>
	    	<input type="text" name="email">
	    </div>
	    <div>
	    	<label>Number of Seats</label>
	    	<input type="text" name="numberOfSeats">
	 	</div>
	 	<div>
	 		<label>Size of luggage</label>
	 		<input type="text" name="luggageSize">
	 	</div>
	</fieldset>
	<input type="hidden" value="${oid}" name="oid">
	<button type="submit" id="submit">Submit</button>
</form>
<#include "footer.ftl">