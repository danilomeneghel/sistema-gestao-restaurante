$(function(){
	$('#estado-select').on('change',function(event){
		var idEstado = $('#estado-select').val(); 
		if(idEstado){
			$('#municipio-div').show();
			$.ajax({
				url:"/ajax/municipioEstado",
				type:"get",
				data:{
					idEstado: idEstado
				},
				success:function(response){
					$('#municipio-select').empty();
					$('#municipio-select').append($('<option>', {
					    value: "",
					    text:  'Escolha o Municipio'
					}));
					$.each(response,function(key,value){
						$('#municipio-select').append($('<option>', {
						    value: key,
						    text:  value
						}));
					});						
				}
			});
		}else{
			$('#municipio-div').hide();
			$('#bairro-select').empty();
			$('#bairro-select').append($('<option>', {
					    value: "",
					    text:  'Escolha o Bairro'
					}));
		}
		$('#bairro-div').hide();
	});
	$('#municipio-select').on('change',function(event){
		var idMunicipio= $('#municipio-select').val(); 
		if(idMunicipio){
			$('#bairro-div').show();
			
			$.ajax({
				url:"/ajax/bairroMunicipio",
				type:"get",
				data:{
					idMunicipio: idMunicipio
				},
				success:function(response){
					$('#bairro-select').empty();
					$('#bairro-select').append($('<option>', {
					    value: "",
					    text:  'Escolha o Bairro'
					}));
					$.each(response,function(key,value){
						$('#bairro-select').append($('<option>', {
						    value: key,
						    text:  value
						}));
					});						
				}
			});
			
		}else{
			$('#bairro-div').hide();
		}
	});
});
