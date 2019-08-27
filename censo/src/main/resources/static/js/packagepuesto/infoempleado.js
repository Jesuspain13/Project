
function clickPuesto(e) {

			var id = e;

			var url= 'http://localhost:8080/empleado/info';
			var idToSearch = "div#" + id;
			console.log($(idToSearch).data("empleado"));
				
	    	empleado = $(idToSearch).data("empleado")
	    	var html = generarModal(empleado);
	    	$(".modal-content").html(html);
	    	$("#modal").modal('show');

		};