package ec.gob.dinardap.autorizacion.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import ec.gob.dinardap.autorizacion.constante.ParametroEnum;
import ec.gob.dinardap.autorizacion.pojo.claim.ClaimRequest;
import ec.gob.dinardap.autorizacion.pojo.claim.ClaimResponse;
import ec.gob.dinardap.autorizacion.pojo.token.ValidarTokenRequest;
import ec.gob.dinardap.autorizacion.pojo.token.ValidarTokenResponse;

public class SauWS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private URL url;

	public ValidarTokenResponse validarToken(String ambiente, ValidarTokenRequest validarTokenRequest) {
		Gson gson = new Gson();
		ValidarTokenResponse validarTokenResponse = null;
		String parametros;
		String respuesta;
		
		// Obtener url del servicio
		setUrl(ambiente, "validarToken");
		
		// Convertir objeto con parametros de entrada a formato json
		parametros = gson.toJson(validarTokenRequest);
		parametros = "{\"validateToken\":" + parametros + "}";

		//Invocar servicio
		respuesta = invocarServicio(ambiente, parametros, null);
		
		//Convertir formato json a objeto
		if(respuesta != null) {
			respuesta = respuesta.replace("{\"@nil\":\"true\"}", "\"null\"").replace("@", "");
			validarTokenResponse = gson.fromJson(respuesta, ValidarTokenResponse.class);
		}

		return validarTokenResponse;
	}

	public ClaimResponse obtenerClaims(String ambiente, ClaimRequest claimRequest, String token) {
		ClaimResponse claimResponse = null;
		Gson gson = new Gson();
		String parametros;
		String respuesta;
		
		// Obtener url del servicio
		setUrl(ambiente, "claims");
		
		// Convertir objeto con parametros de entrada a formato json
		parametros = gson.toJson(claimRequest);
		parametros = "{\"getUserClaimValues\":"+parametros+"}";

		//InvocarServicio
		respuesta = invocarServicio(ambiente, parametros, token);

		// Convertir formato json a objeto
		if(respuesta != null)
			claimResponse = gson.fromJson(respuesta, ClaimResponse.class);
		return claimResponse;
	}
	
	public void cerrarSesion() {
		
	}

	private String invocarServicio(String ambiente, String parametros, String token) {
		String respuesta = null;

		try {
			if (getUrl() != null) {
				// Deshabilitar SSL si el ambiente es de pruebas
				if (ambiente.toUpperCase().equals(ParametroEnum.DEVELOPMENT.name()))
					DeshabilitarSSL.disableSslVerification();

				// Establecer conexión con el servidor ws
				HttpURLConnection conn = (HttpURLConnection) getUrl().openConnection();

				// Definir cabeceras
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
				conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
				if(token != null)
					conn.setRequestProperty("Authorization", token);

				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(parametros.getBytes());
				outputStream.flush();

				// Obtener resultasos del ws
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				respuesta = IOUtils.toString(bufferedReader);

				// Desconectarse del servidor
				conn.disconnect();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return respuesta;
	}

	public URL getUrl() {

		return url;
	}

	public void setUrl(String ambiente, String servicio) {
		try {
			if (ambiente.toUpperCase().equals(ParametroEnum.DEVELOPMENT.name()))
				switch(servicio) {
				case "validarToken":
					this.url = new URL("https://sauqa.dinardap.gob.ec/sau/api/v1/serviciosIs/token/validate");
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
					this.url = new URL("https://sau.dinardap.gob.ec/sau/api/v1/serviciosIs/token/validate");
					break;
				case "claims":
					this.url = new URL("https://sau.dinardap.gob.ec/sau/api/v1/user/claim/valuesAuth");
					break;
				case "cerrar":
					this.url = new URL("");
					break;
				}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			this.url = null;
		}
	}

}
