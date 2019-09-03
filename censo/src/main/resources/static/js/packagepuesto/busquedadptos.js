function buscarDpto() {
	var valorSelect = $("select#selectDpto").val();
	var idDivsDepartamento = "div#" + valorSelect;
	
	var divs = $(idDivsDepartamento);
	for (var i = 0; i < divs.length; i++) {
		pintarBordesPuestoBuscado(divs[i]);
	}	
}