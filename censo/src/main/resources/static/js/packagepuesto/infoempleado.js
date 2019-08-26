
function clickPuesto(e) {

			var id = e;

			var url= 'http://localhost:8080/empleado/info';
			console.log(url);
			$.ajax({
    		    url: url,
    		    type: "GET",
    		    data: {
    		        "id" : id
    		    },
    		    contentType: 'application/json',
    		    dataType: 'json',
    		    success: function (data) {
    		    	console.log(data);
    		    	empleado = data;
    		    	var html = generarModal(empleado);
    		    	$(".modal-content").html(html);
    		    	$("#modal").modal('show');
    		    },
    		    error: function(error) {
    		    	console.log(error);
    		    }
    		 });
		};