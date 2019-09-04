//crea un bloque html en posición normal
function crearPuesto(puesto, styleBtn) {
	 var html = `<p class="name d-flex align-items-center justify-content-center text-center">${puesto.empleado.apellido}</p>
			<button type="button" id=${puesto.idPuestoAuto} class="${styleBtn}" data-toggle="modal"
				data-target="modal" onclick="clickPuesto(this.id)">${puesto.idPuesto}</button>
			
		
          <!--Modal: Login with Avatar Form-->`
          return html;
		
	}

//Crea un bloque html en posición inversa
function crearPuestoInverso(puesto, styleBtn) {

	var html = `<button type="button" id=${puesto.idPuestoAuto} data-toggle="modal" data-target="modal"  class="${styleBtn}" 
				onclick="clickPuesto(this.id)">${puesto.idPuesto}</button>
		<p class="name d-flex align-items-center justify-content-center text-center">${puesto.empleado.apellido}</p>
		<!--Modal: Login with Avatar Form-->
         
         <!--Modal: Login with Avatar Form-->`
	return html;
}

// crea el html de un puesto vacío
function crearPuestoVacio(puesto, empleado, styleBtn) {
	var html = `<p class="name d-flex align-items-center justify-content-center text-center">${empleado}</p>
			<button type="button" 
			id=${puesto.idPuestoAuto} class="btn btn-success" data-toggle="modal" 
				data-target="modal" onclick="clickPuesto(this.id)">${puesto.idPuesto}
			</button>
			
			<!--Modal: Login with Avatar Form-->
         
         <!--Modal: Login with Avatar Form-->`
		return html;
}

//crea el html de un puesto inverso vacío
function crearPuestoInversoVacio(puesto, empleado, styleBtn) {
	var html = `<button type="button"
				id=${puesto.idPuestoAuto}  data-toggle="modal" data-target="modal" 
				onclick="clickPuesto(this.id)" class="btn btn-success">${puesto.idPuesto}
				</button>
		<p class="name d-flex align-items-center justify-content-center text-center">${empleado}</p>
		`
		return html;
		
}

function generarModal(puesto) {
	var htmlModal;
	if (!puesto.ocupado) {
		var empleado = "VACIO";
		htmlModal = ` <div  id="modalInsert" >
	       	<hr>
	   	<h6 class="mt-1 mb-2">Estado: ${empleado}</h6>
	       <hr>
			
	       <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Atrás</button>
	       <button type="button" class="btn btn-success btn-sm"
	       onclick="mostrarFormulario(${puesto.idPuestoAuto}, ${idRegistro})">Asignar Empleado</button>

              
        </div>
`
	} else {
	htmlModal = `
        <div class="modal-header">
            <img src="https://mdbootstrap.com/img/Photos/Avatars/img%20%281%29.jpg" alt="avatar" class="rounded-circle img-responsive">
        </div>
        <!--Body-->
        <div  id="modalInsert" >

	       	<hr>
	   	<h6 class="mt-1 mb-2">Nombre: ${puesto.empleado.nombre}</h6>
	   	<h6 class="mt-1 mb-2">Apellidos: ${puesto.empleado.apellido}</h6>
	       <hr>
	
	       <h5 class="mt-1 mb-2">Nº Empleado: ${puesto.empleado.numeroEmpleado}</h5>	                                            
	       <h5 class="mt-1 mb-2">Nick: ${puesto.empleado.nick}</h5>
	       <hr>
	       <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Atrás</button>

              
        </div>

`
	}
		return htmlModal;
}

//comprueba en departamento y devuelve un estilo en función de este
function seleccionarDpto(dpto) {
	
	var styleBtn;
	if(dpto.includes("SANIDAD")){
     	
     	styleBtn ="btn btn-danger";
     	
    } else if (dpto.includes("OPERACIONES MALAGA")) {
    	
    	styleBtn ="btn btn-primary";
    	
		} else if (dpto.includes("CALIDAD")) {
    	
    	styleBtn ="btn btn-secondary";
    	
	} else if (dpto.includes("INDRA FACTORÍA TECNOLÓGICA (IFT)")) {
    	
    	styleBtn ="btn btn-amber";
    	
	} else if (dpto.includes("PROGRAMAS INTERNACIONALES DE ATM")) {
    	
    	styleBtn ="btn btn-warning";
    	
	} else if (dpto.includes("DIRECCION GENERAL TRANSPORTES")) {
    	
    	styleBtn ="btn btn-pink";
    	
	} else if (dpto.includes("NUCLEO ESTRUCTURA CORPORATIVA")) {
		                        	
		 styleBtn ="btn btn-elegant";
		 
	} else if (dpto.includes("DISEÑO Y DESARROLLO DEFENSA")) {
			
		styleBtn ="btn btn-brown";
		
	} else if (dpto.includes("SOLUCIONES GESTION EMPRESARIAL")) {
			
		styleBtn ="btn btn-deep-orange";
     		
    }else{
    	
    	styleBtn ="btn btn-success";
    }
	return styleBtn;
}
