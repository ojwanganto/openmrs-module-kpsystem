
<% if(client) { %>
<fieldset>
	<legend>Client Profile</legend>

	<div>
		<div style="float: left; width: 35%;">

			<div>
				<span class="ke-identifier-type">Name:</span>
				<span class="ke-identifier-value">${client.getGivenName() + " " + client.getMiddleName() + " " + client.getFamilyName()}</span>
			</div>

			<div>
				<span class="ke-identifier-type">Age:</span>
				<span class="ke-identifier-value">${client.getAge()} <small>(${client.birthDate})</small>
				</span>
			</div>

		</div>
		<div style="float: left; width: 35%;">

			<div>
				<span class="ke-identifier-type">Alias:</span>
				<span class="ke-identifier-value">${alias}</span>
			</div>

		</div>
	</div>
</fieldset>
<% } %>
