package es.indra.censo.model.errores.excel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="celda")
public class Celda implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//	private Fila fila;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private ColumnaExcel columna;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoError error;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Fila getFila() {
//		return fila;
//	}
//
//	public void setFila(Fila fila) {
//		this.fila = fila;
//	}

	public ColumnaExcel getColumna() {
		return columna;
	}

	public void setColumna(ColumnaExcel columna) {
		this.columna = columna;
	}

	public TipoError getError() {
		return error;
	}

	public void setError(TipoError error) {
		this.error = error;
	}
	
	

}
