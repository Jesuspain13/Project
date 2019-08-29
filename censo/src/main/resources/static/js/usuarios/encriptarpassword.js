
$(".enviar").click(function() {
			$(".passResult").val("");
			var name = $(".nameInput").val();
			var rolId = $("#rolInput").val();
			var pass = $(".passwordInput").val();
			console.log(pass);
			var passEncoded = window.btoa(pass);
			console.log(passEncoded);
			$(".nameResult").val(name);
			$(".passResult").val(passEncoded);
			$("#rolResult").val(rolId);
			$(".formRegistro").submit();

		})

		function checkPassword() {
			var pass1 = $(".passwordInput").val();
			var passToCheck = $(".passwordToCheck").val();
			if (pass1 == passToCheck) {
				$(".enviar").prop("disabled", false);
				
				$("#errorPassword").prop('hidden', true);
			} else {
				$(".enviar").prop("disabled", true);
		
				$("#errorPassword").prop('hidden', false);
			}
		}


