package ManagedBeans;


import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entidades.Organizacion;
import persistencia.OrganizacionPersistence;


@ManagedBean(name="TablaOrganizacionesView")
//@ViewScoped
public class TablaOrganizacionesView implements Serializable {
    
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Organizacion> organizaciones;
    
   @EJB
   OrganizacionPersistence organizacionBean;
 

   @PostConstruct
   public void init() {
       organizaciones = organizacionBean.obtenerOrganizaciones();
   }
    
   public List<Organizacion> getOrganizaciones() {
       return organizaciones;
   }

}