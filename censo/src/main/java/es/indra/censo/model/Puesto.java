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
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "puesto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPuestoAuto")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Puesto implements Serializable, Cloneable {

	// Definición de los atributos de la tabla Puesto.
	
	public Puesto(String idPuesto, Planta planta, boolean ocupado, Empleado empleado, Registro registro) {
		super();
		this.idPuesto = idPuesto;
		this.planta = planta;
		this.ocupado = ocupado;
		this.empleado = empleado;
		this.registro = registro;

	}
	
	public Puesto() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_puesto_auto")
	private Integer idPuestoAuto;

	

	@Column(name = "id_puesto")
	private String idPuesto;

// Relación de los puestos con la planta, pendiente de ver la implementación por tema de recursividad.

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "id_planta")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private Planta planta;

	private boolean ocupado;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "id_empleado")
	private Empleado empleado;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_registro")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Registro registro;

	@Transient
	private Double valor = 0.0;

	// Implementación de los Getters & Setters de la clase Puesto.

	public String getIdPuesto() {
		return idPuesto;
	}

	public Integer getIdPuestoAuto() {
		return idPuestoAuto;
	}

	public void setIdPuestoAuto(Integer idPuestoAuto) {
		this.idPuestoAuto = idPuestoAuto;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void setIdPuesto(String idPuesto) {
		this.idPuesto = idPuesto;
	}

	public Planta getPlanta() {
		return planta;
	}

	public void setPlanta(Planta planta) {
		this.planta = planta;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public void calcularValor() {

		// List<String> lista = Arrays.asList(stringSeparado);
		Double d;
		String auxParaQuitarA;

		if (getIdPuesto().contains("A")) {
			auxParaQuitarA = getIdPuesto().replace("A", "");
			d = Double.parseDouble(auxParaQuitarA);
			d += 0.6;
		} else if(getIdPuesto().contains("D")) {
			d = 0.0;
		}else {
			d = Double.parseDouble(getIdPuesto());
		}

		this.valor += d;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Id Puesto: " + getIdPuesto();
	}
	public Puesto clonePuesto() throws CloneNotSupportedException, Exception {
		try {
			return (Puesto) this.clone();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
