function mostrarFormulario(idPuesto, idRegistro) {
	$("div#modalInsert").css("display", "none");
	$("div#formularioCrearEmpleado").css("display", "block");
	$("input#inputPuesto").val(idPuesto);
	$("input#registro").val(idRegistro);
}

function esconderFormulario() {
	$("div#modalInsert").css("display", "block");
	$("div#formularioCrearEmpleado").css("display", "none");
}

function recogerDatosYGuardarUsuario() {
	var datos = recogerDatos();
	var url = "http://localhost:8080/empleado/nuevo"
	$.ajax({
		url: url,
		type: "POST",
		data: { "nombre": datos.nombre,
			"apellidos": datos.apellidos,
			"nick": datos.nick,
			"numero": datos.numero,
			"puesto": datos.puesto,
			"registro": datos.registro,
			"ue": datos.ue
		},
	    contentType: 'application/json',
	    dataType: 'json',
	    success: function (data) {
	    	console.log(data);
	    	var puestoAModificar = $("div#"+datos.puesto);
	    	renderizarPuesto(data, puestoAModificar, data.empleado);
	    },
	    error: function (error) {
	    	console.log(error);
	    }
	})
}

function recogerDatos() {
	var nombre = $("input#nombre").val();
	var apellidos = $("input#apellidos").val();
	var nick = $("input#nick").val();
	var numero = $("input#numero").val();
	var puesto = $("input#inputPuesto").val();
	var registro = $("input#registro").val();
	var ue = $("#ue").val();
	
	return {"nombre": nombre,
		"apellidos": apellidos,
		"nick": nick,
		"numero": numero,
		"puesto": puesto,
		"registro": registro,
		"ue": ue};
}