
	var nombre ="[[${planta.getPuestos().get(1).getEmpleado().getApellido()}]]";
	var numeroPuesto = "[[${planta.getPuestos().get(1).getIdPuesto()}]]";

	console.log(nombre);
	var html = `<p class="name text-center">[[${nombre}]]</p>`

      $('div.puestos').append(html);
