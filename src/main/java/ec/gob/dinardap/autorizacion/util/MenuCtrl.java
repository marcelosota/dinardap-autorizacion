package ec.gob.dinardap.autorizacion.util;

import java.io.Serializable;
import java.util.List;

import javax.faces.annotation.ManagedProperty;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import ec.gob.dinardap.seguridad.modelo.Opcion;
import net.bootsfaces.component.dropMenu.DropMenu;
import net.bootsfaces.component.navBar.NavBar;
import net.bootsfaces.component.navBarLinks.NavBarLinks;
import net.bootsfaces.component.navLink.NavLink;

@Named(value="menuCtrl")
@ViewScoped
public class MenuCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3815899305253451542L;

	@Inject
	@ManagedProperty(value="#{autorizacionCtrl}")
	private AutorizacionCtrl autorizacionCtrl;
		
	private NavBar navBar;
	private String ruta;
	
	public MenuCtrl() {
	}

	private NavBar construirMenu() {
		navBar = new NavBar();
		Application application = FacesContext.getCurrentInstance().getApplication();		
		NavBarLinks navBarLinks = (NavBarLinks)application.createComponent(NavBarLinks.COMPONENT_TYPE);
		
		List<Opcion> menu = autorizacionCtrl.getOpciones();
		if(menu == null)
			return navBar;
		
		//Setea la ruta con el contextPath
		setRuta();
		
		for(Opcion item : menu) {
			if(item.getVisible()) {
				if(!item.getOpcions().isEmpty() && item.getOpcions().size() > 0) {
					DropMenu dropMenu = (DropMenu)application.createComponent(DropMenu.COMPONENT_TYPE);
					submenu(item.getOpcions(), dropMenu, application);
					dropMenu.setIconAwesome("birthday-cake");
					dropMenu.setDisplay("display");
					dropMenu.getAttributes().put("value", item.getNombre());
					navBarLinks.getChildren().add(dropMenu);
				}else {
					navBarLinks.getChildren().add(enlace(item, application));
				}
			}
		}
		navBar.getChildren().add(navBarLinks);
		navBar.setBrand("Dinardap");
		navBar.setBrandHref(getRuta()+"/paginas/brand.jsf");
		
		return navBar;
	}
	
	private DropMenu submenu(List<Opcion> item, DropMenu menuPadre, Application application) {
		for(int i = item.size() - 1; i >= 0; i--) {
			if(item.get(i).getVisible()) {
				if(item.get(i).getOpcions().size() > 0) {
					DropMenu menuHijo = (DropMenu)application.createComponent(DropMenu.COMPONENT_TYPE);
					menuHijo = submenu(item.get(i).getOpcions(), menuHijo, application);
					menuHijo.setIconAwesome("birthday-cake");
					menuHijo.setDisplay("display");
					menuHijo.getAttributes().put("value", item.get(i).getNombre());
					menuPadre.getChildren().add(menuHijo);
					
				}else {
					menuPadre.getChildren().add(enlace(item.get(i), application));
				}
			}
		}
		return menuPadre;
	}
	
	private NavLink enlace(Opcion item, Application application) {
		NavLink opcion = (NavLink)application.createComponent(NavLink.COMPONENT_TYPE);
		opcion.setValue(item.getNombre());
		opcion.setHref(getRuta()+item.getUrl());
		return opcion;
	}
	
	private static String getContextPath() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		return servletContext.getContextPath();
	}
	
	public NavBar getNavBar() {
		if(navBar == null)
			navBar = construirMenu();
		return navBar;
	}

	public void setNavBar(NavBar navBar) {
		this.navBar = navBar;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta() {
		this.ruta = getContextPath();
	}

}
