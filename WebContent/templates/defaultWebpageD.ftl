<#include "header.ftl">

<b>Welcome to Smart Mobility</b><br><br>
Required Information<br/><br/>
<table>
<form method="POST" action="drivergui?action=insertOffer">

		
		<tr>
		<th> <label>Departure Address</label></th>
		<th><input type="text" name="street1">
			<input type="text" name="town1"></th>
	    </tr>
		
		<tr>
		<th>	<label>Start Time</label></th>
		<th>	<input type="datetime-local" name="startTime"></th>
	    </tr>
	    <tr>
			<th><label>Arrival Address</label></th>
			<th><input type="text" name="street2">
			<input type="text" name="town2"></th>
	    </tr>

		<tr>
			<th><label>End Time</label></th>
			<th><input type="datetime-local" name="endTime"></th>
	    </tr>

		<tr>
			<th><label>Number of Seats</label></th>
			<th><input type="text" name="numberOfSeats"></th>
	    </tr>
	    
	    <tr>
			<th><label>Trunk Size</label></th>
			<th><input type="text" name="trunkSize"></th>
	    </tr>

		<tr>
			<th><label>Price</label></th>
			<th><input type="text" name="price"></th>
	    </tr>

		<tr>
			<th><label>Name</label></th>
			<th><input type="text" name="name"></th>
	    </tr>
	    
	    <tr>
			<th><label>Email</label></th>
			<th><input type="text" name="email"></th>
	    </tr>
	    <tr><td colspan="2">

	<button type="submit" id="submit">Create Offer</button></td></tr>
</form>
</table> 	
<#include "footer.ftl">