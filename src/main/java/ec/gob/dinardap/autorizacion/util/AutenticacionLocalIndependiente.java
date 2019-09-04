package ec.gob.dinardap.autorizacion.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ec.gob.dinardap.autorizacion.pojo.claim.ClaimRequest;
import ec.gob.dinardap.autorizacion.pojo.claim.ClaimResponse;
import ec.gob.dinardap.autorizacion.pojo.token.ValidarTokenRequest;
import ec.gob.dinardap.autorizacion.pojo.token.ValidarTokenResponse;

@WebServlet("/independiente")
public class AutenticacionLocalIndependiente extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7475490743711625066L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ValidarTokenResponse validarTokenResponse;
		ClaimResponse claimResponse;
		String usuario;
		String accessToken = request.getParameter("access_token");

		//Validar Token
		SauWS sauWs = new SauWS();
		ValidarTokenRequest validarTokenRequest = new ValidarTokenRequest(accessToken, "Bearer");
		validarTokenResponse = sauWs.validarToken(request.getServletContext().getInitParameter("javax.faces.PROJECT_STAGE"), validarTokenRequest);
		
		if(validarTokenResponse != null && validarTokenResponse.getValidateResponse() != null 
			&& validarTokenResponse.getValidateResponse().getReturn() != null 
			&& Boolean.parseBoolean(validarTokenResponse.getValidateResponse().getReturn().getValid()) 
			&& Integer.parseInt(validarTokenResponse.getValidateResponse().getReturn().getExpiryTime()) > 0 
			&& !validarTokenResponse.getValidateResponse().getReturn().getAuthorizedUser().equals("null")) {
				
			//System.out.println("access_token: "+accessToken);
			usuario = validarTokenResponse.getValidateResponse().getReturn().getAuthorizedUser();
			usuario = usuario.replace("carbon.super","");
					
			//Obtener Perfil
			ClaimRequest claimRequest = new ClaimRequest();
			claimRequest.setCodigo(request.getServletContext().getInitParameter("sistema.identificacion"));
			claimRequest.setProfileName("default");
			claimRequest.setUserName(usuario);
					
			claimResponse = sauWs.obtenerClaims(request.getServletContext().getInitParameter("javax.faces.PROJECT_STAGE"), 
			claimRequest, request.getParameter("access_token"));
			claimResponse.setRole(claimResponse.getRole().replace("Internal/everyone,", ""));
					
			//Crear session
			HttpSession session = request.getSession(true);
			session.setAttribute("accessToken", accessToken);
			session.setAttribute("usuario", claimResponse.getUsername());
			session.setAttribute("nombreUsuario", claimResponse.getFullname());
			session.setAttribute("perfil", claimResponse.getRole());
			System.out.println(session.getAttribute("perfil"));
			response.sendRedirect("paginas/brand.jsf");
		}else
			response.sendRedirect(request.getContextPath()+"/errorSesion.jsf");
	}

}
