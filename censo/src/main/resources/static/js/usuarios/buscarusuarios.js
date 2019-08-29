
$( "#buscar" ).click(function() {
	

	

		var x = $("#usuarioBuscar").val();
		
		var s = $("#status0").val();
		
	
		var usuarios;
		var usuario;
		
		var status= `<i class="fas fa-times active-false"></i>`;
		
		

		const url = 'http://localhost:8080/usuarios/buscar';
		
		
		var htmlWithRowsToRender;


		$.ajax({
    		    url: url,
    		    type: "GET",
    		    data: {
    		        "nombre" : x,
    		        
    		    },
    		    contentType: 'application/json',
    		    dataType: 'json',
    		    success: function (data) {
    		    	usuarios = data;
    		    	for (var i = 0; i < usuarios.length; i++) {
    		    		usuario = usuarios[i];
    		    		var idStatus = "status" + usuario.id;
    		    		urlDelete = "/usuarios/eliminar/" + usuario.id;
    		    		urlUpdate = "/usuarios/modificar/" + usuario.id;
    		    		
    		    		console.log ( "buscar usu:" + idStatus);
    		    		
    		    		var enabled;
    		    		$("div#formularioGuardar").css("display", "none")
    		    		
    		    		if (data.length<1){
    		    			
    		    			
    		    		}
    		    		
    		    		$("div#buscarTabla").css("display", "block")
    		    		
    		    		if (usuario.enabled) {
    		    			enabled = 1;
    		    		} else {
    		    			enabled = 0;
    		    		}
    		    		
    		    		
    		    		if (usuario.enabled){
    		    			status = `<i class="fas fa-check active-true"></i>`;
    		    		
    		    		}
    		    		var authoritiesToShow = generarListaRoles(usuario.roles)
    		    		htmlWithRowsToRender = htmlWithRowsToRender + `<tr>
    							<td class="align-middle">${usuario.username}</td>
    							<td class="align-middle"> <ul>${authoritiesToShow}</ul></td>
    							<td class="text-center align-middle" id="${idStatus}" value="${enabled}"> ${status}</td>
    							<td  class="align-middle">
    								<a  class="btn btn-warning text-center" href=${urlUpdate}><i class="fas fa-edit mr-1"></i></a>
    							</td>
    							<td  class="align-middle">
    		 						<button  class="btn btn-danger text-center"  value=${usuario.id} onclick="enviarModificacionEstado(this)">
    								<i class="fas fa-times mr-1"></i></button>
    							</td>
    						
    							    							
    						</tr>
    						`;
    	    		    }
    		    	$("#tableBody").html(htmlWithRowsToRender)
    		    	}
	            


            });
	
});

function generarListaRoles(roles) {
	console.log(roles)
	var html = "";
	for (var i = 0; i < roles.length; i++) {
		html = html + `
			<ul class="list-group-flush">
			<li class="list-group-item rol-color">${roles[i].authority}</li>
			</ul>
		`;
	}
	console.log(html)
	return html;
}