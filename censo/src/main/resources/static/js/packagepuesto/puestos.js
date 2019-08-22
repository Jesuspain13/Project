function disposicionPuesto(iteracionDiv, puesto, nameModal, idModal, styleBtn) {
	var html;
	if (iteracionDiv.className.includes('puestosInv')) {
		html = crearPuestoInverso(puesto, nameModal, idModal, styleBtn);
      
    } else {
    	html = crearPuesto(puesto, nameModal, idModal, styleBtn);                         
				
    }
    return html;
}

function disposicionPuestoVacio(iteracionDiv, puesto, empleado, nameModal, idModal, styleBtn) {
	var html;
	if (iteracionDiv.className.includes('puestosInv')) {                       
        html = crearPuestoInversoVacio(puesto, empleado, nameModal, idModal, styleBtn);
    } else {
        html = crearPuestoVacio(puesto, empleado, nameModal, idModal, styleBtn);
				
    }
    return html;
}

function seleccionarPuestoOcupadoOVacio(puesto, iteaciónDiv, i) {
	
	var empleado;
	var styleBtn;
	var html; 
	var nameModal = "#modalLoginAvatar" + i;
	var idModal = "modalLoginAvatar" + i;
	if (puesto.ocupado) {
		//si el puesto está ocupado
        empleado = puesto.empleado;

        var dpto = empleado.ue.nombreUeRepercutible;
       
        if(dpto == null){
        	
          	dpto = empleado.ue;
          
       } 
		styleBtn = seleccionarDpto(dpto);
       

        
		html = disposicionPuesto(iteaciónDiv, puesto, nameModal, idModal, styleBtn);
          
        

    } else {
    	//si el puesto está vacío
        empleado = "VACIO";

        html = disposicionPuestoVacio(iteaciónDiv, puesto, empleado, nameModal, idModal, styleBtn);
        
    }
    return html;
}