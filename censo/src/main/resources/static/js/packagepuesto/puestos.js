function disposicionPuesto(iteracionDiv, puesto, styleBtn) {
	var html;
	if (iteracionDiv.className.includes('puestosInv')) {
		html = crearPuestoInverso(puesto, styleBtn);
      
    } else {
    	html = crearPuesto(puesto, styleBtn);                         
				
    }
    return html;
}

function disposicionPuestoVacio(iteracionDiv, puesto, empleado, styleBtn) {
	var html;
	if (iteracionDiv.className.includes('puestosInv')) {                       
        html = crearPuestoInversoVacio(puesto, empleado, styleBtn);
    } else {
        html = crearPuestoVacio(puesto, empleado, styleBtn);
				
    }
    return html;
}

function seleccionarPuestoOcupadoOVacio(puesto, iteaciónDiv, i) {
	
	var empleado;
	var styleBtn;
	var html; 

	if (puesto.ocupado) {
		//si el puesto está ocupado
        empleado = puesto.empleado;

        var dpto = empleado.ue.ueRepercutible.nombreUeRepercutible;

        if(dpto == null){
        	
          	dpto = empleado.ue.ueRepercutible;
          	
       } 
		styleBtn = seleccionarDpto(dpto);
       

        
		html = disposicionPuesto(iteaciónDiv, puesto, styleBtn);
          
        

    } else {
    	//si el puesto está vacío
        empleado = "VACIO";

        html = disposicionPuestoVacio(iteaciónDiv, puesto, empleado, styleBtn);
        
    }
    return html;
}

function pintarBordesPuestoBuscado(iteaciónDiv) {
	var classBootstrap = $(iteaciónDiv).prop('class');
	var newClass = classBootstrap + " border-success rounded";

	$(iteaciónDiv).prop("class", newClass);
	$(iteaciónDiv).css({"border" : "0.15rem",
		"border-style" : "solid"});
}

function añadirDepartamento(iteracionDiv, nombreUe) {
	var clase = $(iteracionDiv).prop('class');
	var claseModificada = clase + " dpto" + nombreUe;
	$(iteracionDiv).prop('class', claseModificada)
}

function renderizarPuesto(puesto, iteracionDiv, idUsuarioBuscado) {
	var html = seleccionarPuestoOcupadoOVacio(puesto, iteracionDiv);
	$(iteracionDiv).data("puesto", puesto);
	$(iteracionDiv).attr("id", puesto.idPuestoAuto);

	
	$(iteracionDiv).html(html);
	
	//añadir departamento en la clase
	if (puesto != null && puesto.empleado != null) {
		añadirDepartamento(iteracionDiv, puesto.empleado.ue.ueRepercutible.idAuto);
	}
	//pinta los bordes si es el usuario buscado
	if (idUsuarioBuscado != null && idUsuarioBuscado.puesto.idPuestoAuto == puesto.idPuestoAuto) {
		pintarBordesPuestoBuscado(iteracionDiv);
	} 
}