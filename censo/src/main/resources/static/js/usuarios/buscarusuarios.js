
$( "#buscar" ).click(function() {

		var x = $("#usuarioBuscar").val()

		const url = 'http://localhost:8080/listausuarios/buscar';

		$.ajax({
    		    url: url,
    		    type: "GET",
    		    data: {
    		        "nombre" : x
    		    },
    		    contentType: 'application/json',
    		    dataType: 'json',
    		    success: function (data) {
	            console.log (data)
				$("#myTabContent").html(`<p>HOLA</p>`);
    		    }	

            });
	
  
});