//busca los puestos con el departamento seleccionado
function buscarDpto() {
		limpiarDptosSeleccionados();
		var valorSelect = $("select#selectDpto").val();

		var idDivsDepartamento = ".dpto" + valorSelect;
		
		var divs = $(idDivsDepartamento);
		
		if (divs.length == 0) {
	
			$("p#msgDptoNoEncontrado").css("display", "block");
		} else {
			$("p#msgDptoNoEncontrado").css("display", "none");
			for(var i = 0; i < divs.length; i++) {
	
				pintarBordesPuestoBuscado(divs[i]);
			}
		}	
}

//elimina los bordes de los puestos buscados antes
function limpiarDptosSeleccionados() {
	var divs = $(".border-success");
	if (divs.length > 0) {
		$(".rounded").removeClass("rounded");
		divs.css({"border" : "0rem",
			"border-style" : "none"});
			
		divs.removeClass("border-success");
	}
	
}