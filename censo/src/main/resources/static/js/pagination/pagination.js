$("button").click(function() {
	$.ajax({
		url: "http://localhost:8080/empleado/buscar/nombre",
		type: "GET",
		//data: 
	    contentType: 'application/json',
	    dataType: 'json',
	    success: function (data) {
	    	console.log(data);
	    	$("div.col-md-10").html(`<p>${data.content}</p>`)
	    }
	})
})