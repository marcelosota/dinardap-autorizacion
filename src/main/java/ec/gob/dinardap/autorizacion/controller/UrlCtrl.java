package ec.gob.dinardap.autorizacion.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ejb.EJB;

import ec.gob.dinardap.autorizacion.constante.ParametroEnum;
import ec.gob.dinardap.seguridad.servicio.ParametroServicio;

public class UrlCtrl {

	@EJB
	ParametroServicio parametroServicio;
	
	private URL url;

	public URL getUrl() {
		return url;
	}

	public void setUrl(String ambiente, String servicio) {
		try {
			if (ambiente.toUpperCase().equals(ParametroEnum.DEVELOPMENT.name()))
				switch(servicio) {
				case "validarToken":
					this.url = new URL(parametroServicio.findByPk(ParametroEnum.SAUQA_VALIDAR.name()).getValor());
					//this.url = new URL("https://sauqa.dinardap.gob.ec/sau/api/v1/serviciosIs/token/validate");
					break;
				case "claims":
					this.url = new URL("https://sauqa.dinardap.gob.ec/sau/api/v1/user/claim/valuesAuth");
					break;
				case "cerrar":
					this.url = new URL("https://sauqa.dinardap.gob.ec/oidc/logout");
				}				
			else
				switch(servicio) {
				case "validarToken":
					this.url = new URL(parametroServicio.findByPk(ParametroEnum.SAUPROD_VALIDAR.name()).getValor());
					break;
				case "claims":
					break;
				case "cerrar":
					break;
				}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			this.url = null;
		}
	}
}
