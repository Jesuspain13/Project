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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ue_repercutible")
public class UeRepercutible implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UeRepercutible() {
		// TODO Auto-generated constructor stub
		this.ues = new ArrayList<Ue>();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer idAuto;
	
	@Column(name="id_ue_repercutible")
	private String idUeRepercutible;
	
	@Column(name="nombre_ue_repercutible")
	private String nombreUeRepercutible;
	
	@OneToMany(mappedBy="ueRepercutible", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private List<Ue> ues;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Registro registro;

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public Integer getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(Integer idAuto) {
		this.idAuto = idAuto;
	}

	public String getIdUeRepercutible() {
		return idUeRepercutible;
	}

	public void setIdUeRepercutible(String idUeRepercutible) {
		this.idUeRepercutible = idUeRepercutible;
	}

	

	public String getNombreUeRepercutible() {
		return nombreUeRepercutible;
	}

	public void setNombreUeRepercutible(String nombreUeRepercutible) {
		this.nombreUeRepercutible = nombreUeRepercutible;
	}

	public List<Ue> getUes() {
		return ues;
	}

	public void setUes(List<Ue> ues) {
		this.ues = ues;
	}
	
	public void addUe(Ue ue) {
		this.ues.add(ue);
	}
	
}
