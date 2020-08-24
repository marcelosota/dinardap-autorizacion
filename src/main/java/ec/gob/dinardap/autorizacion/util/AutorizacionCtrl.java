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
			// opciones = opcionDao.obtenerOpcionesPorPerfil1(misPerfiles);
			opciones = opcionDao.obtenerOpciones(perfil);
		}
	}
	
	/**
	 * @param perfil
	 */
	public void establecerMenuPerfilId(List<String> perfil) throws Exception {
		if (opciones == null) {
			//opciones = opcionDao.obtenerOpcionesPorPerfil1(perfil);
			opciones = opcionDao.obtenerOpcionesPerfilId(perfil);
		}
	}

	/**
	 * @param url
	 * @return
	 */
	public boolean getAccesoOpcionUrl(String url) {
		for (Opcion o : opciones) {
			if (o.getOpcions().size() > 0) {
				for (Opcion op : o.getOpcions()) {
					if (op.getUrl().equals(url)) {
						return true;
					}
				}
			} else if (o.getUrl().equals(url)) {
				return true;
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
