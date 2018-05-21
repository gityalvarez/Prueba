package persistencia;

import entidades.Organizacion;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class OrganizacionPersistence
 */
@Stateless
public class OrganizacionPersistence  {
	
	@PersistenceContext(unitName="Test")
	private EntityManager em;
	
	/**
     * Default constructor. 
     */
    public OrganizacionPersistence() {
        // TODO Auto-generated constructor stub    	
    }     	
    
	
	public boolean agregarOrganizacion(String nombre) {
		Organizacion o = obtenerOrganizacionPorNombre(nombre);
		if (o == null) {		
			Organizacion no = new Organizacion();
			no.setNombre(nombre);
			em.persist(no);
			return true;
		} else {
			return false;
		}		
	}	
	
	
	public Organizacion obtenerOrganizacion(int id) {
		return (Organizacion) em.find(Organizacion.class, id);
	}	
	
	
	public List<Organizacion> obtenerOrganizaciones(){
		return (List<Organizacion>) em.createNamedQuery("Organizacion.findAll", Organizacion.class).getResultList();
	}
	
	
	public Organizacion obtenerOrganizacionPorNombre(String nombre) {
		List<Organizacion> lo = em.createNamedQuery("Organizacion.findByNombre", Organizacion.class).setParameter("nombre", nombre).getResultList();
		if (lo.isEmpty()) {
			return null;
		}
		else {
			return lo.get(0);
		}
	}	
	/*
	public List<Penca> obtenerPencasOrganizacion(int id) {
		Organizacion o = obtenerOrganizacion(id);
		if (o != null) {
			List<Penca> lp = (List<Penca>) em.createNamedQuery("Penca.findByOrganizacion", Penca.class).setParameter("organizacion", o).getResultList();
			return lp;
		} else {
			return null;
		}	
	}	
	*/
	/*
	public boolean agregarPencaEnOrganizacion(int id, Penca penca) {
		Organizacion o = obtenerOrganizacion(id);
		if (o != null) {
			em.getTransaction().begin();
			penca.setOrganizacion(o);			
			em.getTransaction().commit();
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean eliminarPencaEnOrganizacion(int id, Penca penca) {
		Organizacion o = obtenerOrganizacion(id);
		if (o != null) {	
			em.getTransaction().begin();
			penca.setOrganizacion(null);			
			em.getTransaction().commit();
			return true;
		} else {
			return false;
		}	
	}*/
	
	
	public boolean eliminarOrganizacion(int id) {
		Organizacion o = obtenerOrganizacion(id);
		if (o != null) {				
			em.remove(o);
			return true;
		} else {
			return false;
		}
	}		

}
