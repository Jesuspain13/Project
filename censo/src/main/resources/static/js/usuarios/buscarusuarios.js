
$( "#buscar" ).click(function() {
	
	$("div#formularioGuardar").css("display", "none")
	$("div#buscarTabla").css("display", "block")
	

		var x = $("#usuarioBuscar").val();
		var usuarios;
		var usuario;
		var status= `<i class="fas fa-times active-false"></i>`;

		const url = 'http://localhost:8080/usuarios/buscar';
		
		
		var htmlWithRowsToRender;


		$.ajax({
    		    url: url,
    		    type: "GET",
    		    data: {
    		        "nombre" : x
    		    },
    		    contentType: 'application/json',
    		    dataType: 'json',
    		    success: function (data) {
    		    	usuarios = data;
    		    	for (var i = 0; i < usuarios.length; i++) {
    		    		usuario = usuarios[i];
    		    		urlDelete = "/usuarios/eliminar/" + usuario.id;
    		    		urlUpdate = "/usuarios/modificar/" + usuario.id;
    		    		
    		    		if (usuario.enabled){
    		    			status = `<i class="fas fa-check active-true"></i>`;
    		    		
    		    		}
    		    		htmlWithRowsToRender = htmlWithRowsToRender + `<tr>
    							<td class="align-middle">${usuario.username}</td>
    							<td class="align-middle"> ${usuario.roles[0].authority}</td>
    							<td class="text-center align-middle"> ${status}</td>
    							<td>
    								<a class="btn btn-success" href=${urlUpdate}><i class="fas fa-edit mr-1"></i></a>
    							</td>
    							<td>
    								<a class="btn btn-danger" href=${urlDelete}><i class="fas fa-trash-alt mr-1"></i></a>
    							</td>
    						</th>
    						`;
    	    		    }
    		    	$("#tableBody").html(htmlWithRowsToRender)
    		    	}
	            
					

            });
	
});