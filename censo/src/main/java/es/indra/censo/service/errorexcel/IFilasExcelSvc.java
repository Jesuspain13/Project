package es.indra.censo.service.errorexcel;

import java.util.List;

import org.springframework.data.domain.Page;

import es.indra.censo.model.errores.excel.Fila;


public interface IFilasExcelSvc {
	
	public Page<Fila> encontrarErroresDelRegistro(Integer idRegistro, int pageNumber) throws Exception;

	public Page<Fila> guardarErrores(List<Fila> e) throws Exception;
	
	public void ignorarErrores(Fila e) throws Exception;
}
