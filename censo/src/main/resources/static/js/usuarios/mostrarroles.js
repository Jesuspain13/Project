function mostrarRoles(roles, username) {
	
	var rolesRow;
	var rolIter;
	for (var i = 0; i < roles.length; i++) {
		
		rolIter = roles[i];
		console.log(rolIter);
		rolesRow = rolesRow + `<tr>
	    <td>${rolIter.authority}</td>
	    <td>
	 
	    
	    	<button  class="btn btn-danger btnBorrarRol" value='${rolIter.rolId}'>Borrar</button>
	 
	    </td>
	   
	</tr>
	`
	}
	$("#rolesBodyTable").html(rolesRow);


$(".btnBorrarRol").click(function() {
	var userId = $("input#inputUserId").val();
	var rolId = $(this).val();
	console.log("idUsuario: " + userId + " rolId: " + rolId);
});

}