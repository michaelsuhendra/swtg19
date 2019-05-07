<#include "header.ftl">

<b>Welcome to Smart Mobility</b>

<table id="availableOffer" >
	<tr bgcolor="green">
		<th>#</th>
		<th>Start Place</th>
		<th>Start Time</th>
		<th>End Place</th>
		<th>End Time</th>
		<th>Number of seats</th>
		<th>Luggage Size</th>
	</tr>
	<#list availableOffers as offer>
	<tr>
		<th><a href="passengergui?action=selectOffer&amp;oid=${offer.id}" title="Book Offer">${offer.id}</a></th>
		<th>${offer.startPlace}</th>
		<th>${offer.startTime}</th>
		<th>${offer.endPlace}</th>
		<th>${offer.endTime}</th>
		<th>${offer.numberOfSeats}</th>
		<th>${offer.luggageSize}</th>
	</tr>
	</#list>
</table>
<#include "footer.ftl">