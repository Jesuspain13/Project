package es.indra.censo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "empleado")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEmpleadoAuto")
public class Empleado implements Serializable {
	
	public Empleado() {
		
	}

	// Definición de los atributos de la tabla Empleado

	public Empleado(Integer numeroEmpleado, String nick, String nombre, String apellido, Registro registro, Ue ue) {
		super();
		this.numeroEmpleado = numeroEmpleado;
		this.nick = nick;
		this.nombre = nombre;
		this.apellido = apellido;
		this.registro = registro;
		this.ue = ue;
		//this.puesto = puesto;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_empleado_auto")
	private Integer idEmpleadoAuto;

	@Column(name = "numero_empleado")
	private Integer numeroEmpleado;

	private String nick;
	private String nombre;
	private String apellido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Registro registro;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ue")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Ue ue;

	@OneToOne(mappedBy = "empleado", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Puesto puesto;

	// Implementación de los Getters & Setters de la clase Empleado.

	public Integer getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(Integer numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public Integer getIdEmpleadoAuto() {
		return idEmpleadoAuto;
	}

	public void setIdEmpleadoAuto(Integer idEmpleadoAuto) {
		this.idEmpleadoAuto = idEmpleadoAuto;
	}

	public Ue getUe() {
		return ue;
	}

	public void setUe(Ue ue) {
		this.ue = ue;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Puesto getPuesto() {
		return this.puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
