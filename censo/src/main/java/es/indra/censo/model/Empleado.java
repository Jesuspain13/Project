package es.indra.censo.model;

import java.io.Serializable;

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

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

	// Definición de los atributos de la tabla Empleado

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empleado_auto")
	private Integer idEmpleadoAuto;
	
	@NaturalId
	@Column(name = "numero_empleado")
	private Integer numeroEmpleado;

	private String nick;
	private String nombre;
	private String apellido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registro")
	private Registro registro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ue")
	private Ue ue;
	
	@OneToOne(mappedBy="empleado", fetch = FetchType.LAZY)
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
