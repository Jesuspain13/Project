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

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "complejos")
public class Complejo implements Serializable {
	
	public Complejo() {
		this.edificios = new ArrayList<Edificio>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NaturalId
	@Column(name = "id_complejo")
	private String idComplejo;

	@Column(name = "nombre_complejo")
	private String nombreComplejo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	private Registro registro;
	
	@OneToMany(mappedBy="complejo",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Edificio> edificios;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdComplejo() {
		return idComplejo;
	}

	public void setIdComplejo(String idComplejo) {
		this.idComplejo = idComplejo;
	}

	public String getNombreComplejo() {
		return nombreComplejo;
	}
	

	public void setNombreComplejo(String nombreComplejo) {
		this.nombreComplejo = nombreComplejo;
	}

	public Registro getRegistro() {
		return registro;
	}
	

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public void addEdificio(Edificio e) {
		this.edificios.add(e);
	}


	public List<Edificio> getEdificios() {
		return edificios;
	}

	public void setEdificios(List<Edificio> edificios) {
		this.edificios = edificios;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
