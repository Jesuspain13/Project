package es.indra.censo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "plantas")
public class Planta implements Serializable {
	
	public Planta() {
		this.puestos = new ArrayList<Puesto>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "nombre_planta")
	private String nombrePlanta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	private Registro registro;

	@OneToMany(mappedBy="planta",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Puesto> puestos;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_edificio")
	private Edificio edificio;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombrePlanta() {
		return nombrePlanta;
	}

	public void setNombrePlanta(String nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}

	public Registro getRegistro() {
		return registro;
	}

	public List<Puesto> getPuestos() {
		return puestos;
	}

	public void setPuestos(List<Puesto> puestos) {
		this.puestos = puestos;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	
	public void addPuesto(Puesto p) {
		this.puestos.add(p);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
