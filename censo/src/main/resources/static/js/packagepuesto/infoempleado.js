
function clickPuesto(e) {

			var id = e;

		
			var idToSearch = "div#" + id;
		
			
	    	var puesto = $(idToSearch).data("puesto");
	    	
	    	var html = generarModal(puesto);
	    	$("#infoEmpleado").html(html);
	    	$("#modal").modal('show');

		};