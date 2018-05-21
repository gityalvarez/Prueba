package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Torneo;
@Stateless
public class TorneoPersistence {

	@PersistenceContext(unitName = "Test")
	private EntityManager em;
	
	//private static TorneoPersistence instancia = null;
	/*private TorneoPersistence() {}
	
	public static TorneoPersistence getInstancia() {
		if(instancia == null) {
			instancia = new TorneoPersistence();
		}
		return instancia;
	}
	*/
	public void Torneo() {
		
	}
	public boolean agregarTorneo(String nombre) {
		System.out.println(" ANTES: Controller -> agregarTorneo");
		Torneo t = new Torneo();
		t.setNombre(nombre);
		em.persist(t);
		return true;
		/*try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
			System.out.println(" DESPUES: Controller -> agregarTorneo");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		
	}
	
	public Torneo getTorneo(int id) {
		return em.find(Torneo.class,id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Torneo> getAllTorneos(){	 
		
		return (List<Torneo>) em.createNamedQuery("Torneo.findAll",Torneo.class).getResultList();
	}
}