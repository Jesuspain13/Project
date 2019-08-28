function mostrarRoles(roles, username) {
	
	var rolesRow;
	var rolIter;
	for (var i = 0; i < roles.length; i++) {
		
		rolIter = roles[i];
		console.log(rolIter);
		rolesRow = rolesRow + `<tr id='${rolIter.rolId}'>
	    <td>${rolIter.authority}</td>
	    <td>
	    	<button  class="btn btn-danger btnBorrarRol" value='${rolIter.rolId}' onclick="eliminarRol(this)">Borrar</button>
	 
	    </td>
	   
	</tr>
	`
	}
	$("#rolesBodyTable").html(rolesRow);

}

function eliminarRol(button) {
	var url = "http://localhost:8080/usuarios/delete/rol"
	var userId = $("input#inputUserId").val();
	var rolId = $(button).val();
	var row = "tr#" + rolId;

	
	$.ajax({
	    url: url,
	    type: "GET",
	    data: {
	        "rolId" : rolId,
	        "usuarioId" : userId
	    },
	    contentType: 'application/json',
	    dataType: 'json',
	    success: function (data) {
	    	console.log(data);
	    	console.log("Se ha eliminado el rol con exito")
	    	$(row).remove();
	    },
	    error: function(error) {
	    	console.log(error);
	    	console.log("Error al eliminar el rol");
	    }
	});
}