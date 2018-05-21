package ManagedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import persistencia.TorneoPersistence;

@ManagedBean(name = "AgregarTorneoView")
@ViewScoped
public class AgregarTorneoView implements Serializable {

	/**
	 * 
	 */
	@EJB
	TorneoPersistence beanTorneo;
	private static final long serialVersionUID = 1L;

	private String nombre;

	public AgregarTorneoView() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void save() {
		System.out.println("Hola antes de persistir");
		//TorneoPersistence.getInstancia().agregarTorneo(nombre);	
		beanTorneo.agregarTorneo(nombre);
		System.out.println("Chau despues de persistir");
	}
}