package ec.gob.dinardap.autorizacion.util;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ec.gob.dinardap.seguridad.dao.OpcionDao;
import ec.gob.dinardap.seguridad.modelo.Opcion;

@Named(value = "autorizacionCtrl")
@SessionScoped
public class AutorizacionCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -427033854229046157L;
	
	@EJB
	private OpcionDao opcionDao;
	
	private List<Opcion> opciones;

	/**
	 * @param perfil
	 */
	public void establecerMenu(List<String> perfil) throws Exception {
		if (opciones == null) {
			//opciones = opcionDao.obtenerOpcionesPorPerfil(perfilId);
			opciones = opcionDao.obtenerOpciones(perfil);
		}
	}
	
	/**
	 * @param url
	 * @return
	 */
	public boolean getAccesoOpcionUrl(String url) {
		for (Opcion o : opciones) {
			if (o.getUrl() != null) {
				if (o.getUrl().equals(url)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Opcion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<Opcion> opciones) {
		this.opciones = opciones;
	}
}
