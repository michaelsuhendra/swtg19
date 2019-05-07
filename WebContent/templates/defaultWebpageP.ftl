<#include "header.ftl">

<b>Welcome to Smart Mobility</b><br><br>
Required Information<br/>
<table>
<form method="POST" action="passengergui?action=browseAvailableOffer">
		
		<fieldset id="browseAvailableOffer">
		<tr>
			<th><label>Departure Address</label></th>
			<th><input type="text" name="street1">
			<input type="text" name="town1"></th>
	    </tr>
		
		<tr>
			<th><label>Departure Time</label></th>
			<th><input type="datetime-local" name="startTime"></th>
	    </tr>
	    
	    <tr>
			<th><label>Arrival Address</label></th>
			<th><input type="text" name="street2">
			<input type="text" name="town2"></th>
	    </tr>

		<tr>
			<th><label>Arrival Time</label></th>
			<th><input type="datetime-local" name="endTime"></th>
	    </tr>

		<tr>
			<th><label>Number of Seats</label></th>
			<th><input type="text" name="numberOfSeats"></th>
	    </tr>
	    
	    <tr>
			<th><label>Luggage Size</label></th>
			<th><input type="text" name="luggageSize"></th>
	    </tr>

		<tr><td colspan="2">
	</fieldset>
	<button type="submit" id="SelectOfferWebpage" name="SelectOfferWebpage" value="Submit">Search</button></td></tr>
</form>
</table>
<#include "footer.ftl">