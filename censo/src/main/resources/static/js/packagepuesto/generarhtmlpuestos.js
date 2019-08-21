//crea un bloque html en posición normal
function crearPuesto(puesto, nameModal, idModal, styleBtn) {
	 var html = `<p class="name d-flex align-items-center justify-content-center name text-center">${puesto.empleado.apellido}</p>
			<button type="button"  id="button" class="${styleBtn}" data-toggle="modal"
				data-target=${nameModal}>${puesto.idPuesto}</button>
			
			<!--Modal: Login with Avatar Form-->
          <div class="modal fade" id=${idModal} tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                  <!--Content-->
                  <div class="modal-content">

                      <!--Header-->
                      <div class="modal-header">
                          <img src="https://mdbootstrap.com/img/Photos/Avatars/img%20%281%29.jpg" alt="avatar" class="rounded-circle img-responsive">
                      </div>
                      <!--Body-->
                      <div  id="modalInsert" class="modal-body text-center mb-1">

                     	<h4 class="mt-1 mb-2">Nº Puesto: ${puesto.idPuesto}</h4>
                     	<hr>
                 	<h6 class="mt-1 mb-2">Nombre: ${puesto.empleado.nombre}</h6>
                 	<h6 class="mt-1 mb-2">Apellidos: ${puesto.empleado.apellido}</h6>
                     <hr>

                     <h5 class="mt-1 mb-2">Nº Empleado: ${puesto.empleado.numeroEmpleado}</h5>	                                            
                     <h5 class="mt-1 mb-2">Nick: ${puesto.empleado.nick}</h5>
                     <hr>
                     <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Atrás</button>

                            
                      </div>

                  </div>
                  <!--/.Content-->
              </div>
          </div>
          <!--Modal: Login with Avatar Form-->`
          return html;
		
	}

//Crea un bloque html en posición inversa
function crearPuestoInverso(puesto, nameModal, idModal, styleBtn) {

	var html = `<button type="button" id="button" data-toggle="modal" data-target=${nameModal}  class="${styleBtn}" >${puesto.idPuesto}</button>
		<p class="name d-flex align-items-center justify-content-center name text-center">${puesto.empleado.apellido}</p>
		<!--Modal: Login with Avatar Form-->
         <div class="modal fade" id=${idModal} tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
             <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                 <!--Content-->
                 <div class="modal-content">

                     <!--Header-->
                     <div class="modal-header">
                         <img src="https://mdbootstrap.com/img/Photos/Avatars/img%20%281%29.jpg" alt="avatar" class="rounded-circle img-responsive">
                     </div>
                     <!--Body-->
                     <div   class="modal-body text-center mb-1">

                        	<h4 class="mt-1 mb-2">Nº Puesto: ${puesto.idPuesto}</h4>
                        	<hr>
                    	<h6 class="mt-1 mb-2">Nombre: ${puesto.empleado.nombre}</h6>
                    	<h6 class="mt-1 mb-2">Apellidos: ${puesto.empleado.apellido}</h6>
                        <hr>

                        <h5 class="mt-1 mb-2">Nº Empleado: ${puesto.empleado.numeroEmpleado}</h5>		                                            
                        <h5 class="mt-1 mb-2">Nick: ${puesto.empleado.nick}</h5>
                        <hr>
                        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Atrás</button>

                           
                     </div>

                 </div>
                 <!--/.Content-->
             </div>
         </div>
         <!--Modal: Login with Avatar Form-->`
	return html;
}

// crea el html de un puesto vacío
function crearPuestoVacio(puesto, empleado, nameModal, idModal, styleBtn) {
	var html = `<p class="name d-flex align-items-center justify-content-center name text-center">${empleado}</p>
			<button type="button" id="button" class="btn btn-success" data-toggle="modal" 
				data-target=${nameModal}>${puesto.idPuesto}</button>
			
			<!--Modal: Login with Avatar Form-->
         <div class="modal fade" id=${idModal} tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
             <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                 <!--Content-->
                 <div class="modal-content">

                     <!--Header-->
                     <div class="modal-header">
                         <img src="https://mdbootstrap.com/img/Photos/Avatars/img%20%281%29.jpg" alt="avatar" class="rounded-circle img-responsive">
                     </div>
                     <!--Body-->
                     <div  id="modalInsert" class="modal-body text-center mb-1">

                             	<h4 class="mt-1 mb-2">Nº puesto:${puesto.idPuesto}</h4>
                             	<h5 class="mt-1 mb-2">Estado: ${empleado}</h5>
                             	<hr>
                                 <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Atrás</button>

                                 

                           
                     </div>

                 </div>
                 <!--/.Content-->
             </div>
         </div>
         <!--Modal: Login with Avatar Form-->`
		return html;
}

//crea el html de un puesto inverso vacío
function crearPuestoInversoVacio(puesto, empleado, nameModal, idModal, styleBtn) {
	var html = `<button type="button"  id="button" data-toggle="modal" data-target=${nameModal}  class="btn btn-success">${puesto.idPuesto}</button>
		<p class="name d-flex align-items-center justify-content-center name text-center">${empleado}</p>
		<!--Modal: Login with Avatar Form-->
         <div class="modal fade" id=${idModal} tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
             <div class="modal-dialog cascading-modal modal-avatar modal-sm" role="document">
                 <!--Content-->
                 <div class="modal-content">

                     <!--Header-->
                     <div class="modal-header">
                         <img src="https://mdbootstrap.com/img/Photos/Avatars/img%20%281%29.jpg" alt="avatar" class="rounded-circle img-responsive">
                     </div>
                     <!--Body-->
                     <div  id="modalInsert" class="modal-body text-center mb-1">

                             	<h4 class="mt-1 mb-2">Nº puesto: ${puesto.idPuesto}</h4>
                             	<h5 class="mt-1 mb-2">Estado ${empleado}</h5>
                             	<hr>
                                 <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">Atrás</button>

                                 

                           
                     </div>

                 </div>
                 <!--/.Content-->
             </div>
         </div>
         <!--Modal: Login with Avatar Form-->`
		return html;
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
