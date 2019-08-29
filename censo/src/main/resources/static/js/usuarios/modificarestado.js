function enviarModificacionEstado(button) {

	var idUsuario = $(button).val();

	var idStatus = "#status" + idUsuario;

	var statusFalse = `<i class="fas fa-times active-false"></i>`;
	var statusTrue = `<i class="fas fa-check active-true"></i>`;

	var buttonFalse = `btn btn-danger text-center`;
	var buttonTrue = `btn btn-success text-center`;
	
	var iconFalse = `<i class="fas fa-times"></i>`;
	var iconTrue = `<i class="fas fa-check"></i>`;

	
	const url = 'http://localhost:8080/usuarios/modificarEstado';

	$.ajax({
		url : url,
		type : "GET",
		data : {
			"idUsuario" : idUsuario

		},
		contentType : 'application/json',
		dataType : 'json',
		success : function() {

			if ($(idStatus).attr("value") == 0) {
				
				// Si entra aquí la condicion es false y tengo que cambiar a true.
				$(idStatus).attr("value", 1);

				$(button).html(iconFalse);
				$(button).attr("class", buttonFalse)
				$(idStatus).html(statusTrue)
				
			} else if ($(idStatus).attr("value") == 1) {
				
				// Si entra aquí la condicion es true y tengo que cambiar a	false.
				$(idStatus).attr("value", 0);
				
				$(button).html(iconTrue);
				$(button).attr("class", buttonTrue)
				$(idStatus).html(statusFalse)

			}

		},
		error : function(error) {
			console.log(error)
		}

	});

}