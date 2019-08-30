function mostrarRolesUsuario(roles, usuario) {
	
	var rolesRow;
	var rolIter;
	var htmlEach;
	if (roles.length > 0) {
		for (var i = 0; i < roles.length; i++) {
			rolIter = roles[i];
			// creamos la fila con los datos (false -> si se ha añadido -> se
			// renderiza el borrar
			// true -> si se ha borrado -> se renderiza añadir
			console.log(usuario)
			htmlEach = renderizarFila(rolIter.rolId, rolIter.authority, usuario.idUser, false);
			rolesRow = rolesRow + htmlEach;
		}
		$("#rolesBodyTableDelete").html(rolesRow);
	} else {
		$("div#table").append("<p class='alert alert-danger'>Not Rol for this user</p>")
	}
	

}

function añadirRol(button) {
	
	const url = 'http://localhost:8080/usuarios/add/rol'
	// Recogemos datos sobre el rol a añadir
	// contiene el rolId
	var valorBotonAñadir = $(button).val();
	
	var idInputRolAuthority = "#inputRolAuthority" + valorBotonAñadir;

	var valueInputRolAuthority = $(idInputRolAuthority).val();
	// contiene el id del usuario a modificar
	var idInputUserId = "#inputUserId" + valorBotonAñadir;

	var valueInputUserId = $(idInputUserId).val();
	var row = "tr#" + valorBotonAñadir;

   
	// ejecutamos la petición ajax para añadir el rol
	$.ajax({
	    url: url,
	    type: 'GET',
	    data: {
	        "rolId" : valorBotonAñadir,
	        "usuarioId" : valueInputUserId
	    },
	    contentType: 'application/json',
	    dataType: 'json',
	    success: function (data) {
	    	borrarFilaTabla(false, valorBotonAñadir,
	    			valueInputRolAuthority, valueInputUserId);
	    },
	    error: function(error) {
	    	console.log(error);
	    }
	});

}

// crea el html de cada fila con el formulario en función si se ha añadido o
// eliminado un registro
function renderizarFila(rolId, authority, userId, addOrDelete) {
	var idInputUser = "inputUserId" + rolId;
	var idInputAuthority = "inputRolAuthority" + rolId;
	var html
	// si es true es que se ha eliminado un registro y necesitamos una fila de
	// tipo añadir
	if (addOrDelete) {
		 html = `
			<tr id=${rolId}>
			    <td class="td-rol text-center align-middle">${authority}</td>
			    <td class="td-rol text-center align-middle">
			    	<form>
			    		<input type="hidden" id=${idInputUser}
			    		value="${userId}">
			    		<input type="hidden" id=${idInputAuthority}
			    		value="${authority}">
			    	
			    		<button class="btn btn-success" value="${rolId}"
			    	 	type="button" onclick="añadirRol(this)"> <i class="fas fa-plus-circle"></i> </button>
		    		</form>
			 
			    </td>
			   
			</tr>
			`
			// si es false es que se ha añadido un registro y necesitamos una
			// fila de tipo borrar
	} else {
		html = `
		<tr id='${rolId}'>
	    <td class="td-rol text-center align-middle">${authority}</td>
	    <td class="td-rol text-center align-middle">
	    	<form>
	    		<input type="hidden" id=${idInputUser}
	    		value="${userId}">
	    		<input type="hidden" id=${idInputAuthority}
	    		value="${authority}">
	    	
	    		<button class="btn btn-danger" value="${rolId}"
	    	 	type="button" onclick="eliminarRol(this)"><i class="fas fa-minus"></i></button>
    		</form>
	 
	    </td>
	   
	</tr>
	`
	}
	return html;
	
}

function eliminarRol(button) {
	var url = "http://localhost:8080/usuarios/borrar/rol"
	// recogemos los datos de la fila para la consultacontiene el rolId
	var valorBotonAñadir = $(button).val();
	
	var idInputRolAuthority = "#inputRolAuthority" + valorBotonAñadir;
	var valueInputRolAuthority = $(idInputRolAuthority).val();
	// contiene el id del usuario a modificar
	var idInputUserId = "#inputUserId" + valorBotonAñadir;
	var valueInputUserId = $(idInputUserId).val();

	// realizamos la consulta para borrar un registro del usuario
	$.ajax({
	    url: url,
	    type: 'GET',
	    data: {
	        "rolId" : valorBotonAñadir,
	        "usuarioId" : valueInputUserId
	    },
	    contentType: 'application/json',
	    dataType: 'json',
	    success: function (data) {
	    	borrarFilaTabla(true, valorBotonAñadir,
	    			valueInputRolAuthority, valueInputUserId);
	    },
	    error: function(error) {
	    	console.log(error);
	    }
	});
}

/**	
 * Evalua de que tabla tiene que eliminar la fila según ha borrado o añadido un rol
 * borrar -> booleano si ha borrado o no
 * rolId -> id del rol
 * authority -> string con el nombre del permiso
 * userId -> id del usuario de la vista
*/
function borrarFilaTabla(borrar, rolId, authority, userId) {
	//si borrar es true es que se ha borrado
	var idBodyTablaContraria;
	var tablaId;
	
	if (borrar) {
		console.log("Se ha añadido el rol con exito");
		idBodyTablaContraria = "#rolesBodyTableAdd";
		tablaId = "div#table";
	} else {
		console.log("Se ha eliminado el rol con exito");
		idBodyTablaContraria = "#rolesBodyTableDelete"
		tablaId = "div#tableAdd";
	}
	
	var row = "tr#" + rolId;
	$(row).remove();
	checkTabla(tablaId);
	$(idBodyTablaContraria).append(renderizarFila(rolId, authority, userId, borrar));
}




function checkTabla(tabla) {
	var rows = $(tabla + " table tbody tr");
	console.log(rows.length < 1);
	if (rows.length < 1) {
		$(tabla).append('<p class="alert alert-danger">No quedan más roles en la tabla</p>');
	} else {
		$("p.alert-danger").remove();
	}
}

//checkear tabla de añadir roles al inicio
$(document).ready(checkTabla("#tableAdd"));