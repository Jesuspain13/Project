package es.indra.censo.model.errores.excel;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.indra.censo.model.Registro;

@Entity
@Table(name="fila")
public class Fila implements Serializable {

	public Fila() {
	}

	public Fila(Integer fila) {
		super();
		this.numero = fila + 1;

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;

	private Integer numero;
	

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Registro registro;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<Celda> celdas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<Celda> getCeldas() {
		return celdas;
	}

	public void setCeldas(List<Celda> celdas) {
		this.celdas = celdas;
	}
	
	
	
	
}
