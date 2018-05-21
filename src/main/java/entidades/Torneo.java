package entidades;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name = "Torneo.findAll", query="SELECT t FROM Torneo t")
})
public class Torneo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String nombre;

	public Torneo() {
		
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Torneo [nombre=" + nombre + "]";
	}

	
}

