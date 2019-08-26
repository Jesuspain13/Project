
$( "#buscar" ).click(function() {

		var x = $("#usuarioBuscar").val();
		var usuarios;
		var usuario;

		const url = 'http://localhost:8080/usuarios/buscar';
		var htmlToRender = `
		<div class="container">
		<div class="row d-flex justify-content-center">
		<div class="col-md-10 text-center t-flex justify-content-center">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>NOMBRE</th>
						<th>ROL</th>
						<th>ELIMINAR</th>
					</tr>
				</thead>
				<tbody id="tableBody">
					
				</tbody>
			</table>
			</div>
		</div>
		</div>
		</div>
		`;
		
		var htmlWithRows;
		
		$("#myTabContent").html(htmlToRender);
	    

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
    		    		htmlWithRows = htmlWithRows + `<tr>
    							<td class="align-middle">${usuario.username}</td>
    							<td class="align-middle"> ${usuario.roles[0].authority}</td>
    							<td>
    								<a class="btn btn-danger" href=${urlDelete}>Eliminar</a>
    							</td>
    							<td>
    								<a class="btn btn-success" href=${urlUpdate}>Modificar</a>
    							</td>
    						</th>
    						`;
    	    		    }
    		    	$("#tableBody").html(htmlWithRows)
    		    	}
	            
					

            });
	
});