package ec.gob.dinardap.autorizacion.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "FiltroAutorizacioBasico")
public class FiltroAutorizacionBasico implements Filter {

    private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();

    @Resource
    BeanManager beanManager;

    public FiltroAutorizacionBasico() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public static HttpSession getSession() {
        HttpServletRequest request = localRequest.get();
        return (request != null) ? request.getSession() : null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest localHttpServletRequest = (HttpServletRequest) request;
        HttpServletResponse localHttpServletResponse = (HttpServletResponse) response;

        //TODO Verificar que la sesión en el IS esté activa
        //SauWS sauWs = new SauWS();
        //ValidarTokenRequest validarTokenRequest = 
        //		new ValidarTokenRequest(localHttpServletRequest.getSession().getAttribute("accessToken").toString(), "Bearer");
        //ValidarTokenResponse validarTokenResponse = 
        //		sauWs.validarToken(localHttpServletRequest.getServletContext().getInitParameter("javax.faces.PROJECT_STAGE"), validarTokenRequest);
        /*if(validarTokenResponse != null && validarTokenResponse.getValidateResponse() != null 
				&& validarTokenResponse.getValidateResponse().getReturn() != null 
				&& !validarTokenResponse.getValidateResponse().getReturn().getAuthorizedUser().equals("null") 
				&& Boolean.parseBoolean(validarTokenResponse.getValidateResponse().getReturn().getValid()) 
				&& Integer.parseInt(validarTokenResponse.getValidateResponse().getReturn().getExpiryTime()) > 0) {*/
        //if(localHttpServletRequest.getSession().getMaxInactiveInterval()/60 >=  Integer.parseInt(localHttpServletRequest.getServletContext().getInitParameter("session-timeout"))) {
        // acceso al manage bean que administra la autorizacion
        AutorizacionCtrl autorizacionCtrl = getBeanInstance(beanManager, AutorizacionCtrl.class);

        String strUrl = limpiarUri(localHttpServletRequest);

        if (strUrl.contains("/javax.faces.resource/")
                || strUrl.contains("/rfRes/")
                || (!strUrl.endsWith(".jsf") && !strUrl.endsWith(".html"))) {
            chain.doFilter(request, response);
            return;
        }

        // permite el acceso a las paginas que no estan protegidas
        String paginas = localHttpServletRequest.getServletContext().getInitParameter("url.acceso");

        if (paginas != null) {
            // Se limpia los caracteres no validos para el URL (saltos de linea, espacios y tabuladores)
            paginas = paginas.replace(System.getProperty("line.separator"), "");
            paginas = paginas.replace(" ", "");
            paginas = paginas.replace("\t", "");

            // valido si la lista en nula, si existe se agregan las URL
            String[] pag = paginas.split(",");
            for (int i = 0; i < pag.length; i++) {
                if (pag[i].contains(strUrl)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        try {
            // establece el usuario y el perfil
            List<String> perfil = new ArrayList<String>(Arrays.asList(localHttpServletRequest.getSession().getAttribute("perfil").toString().split(",")));
            autorizacionCtrl.establecerMenuPerfilId(perfil);

        } catch (Exception e) {
            System.out.println("Se va por el catch");
            localHttpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }

        if (autorizacionCtrl.getAccesoOpcionUrl(strUrl)) {
            chain.doFilter(request, response);
            return;
        } else {
            localHttpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "NO TIENE PERMISOS PARA EL URL INGRESADO");
        }
        //localHttpServletResponse.sendRedirect(localHttpServletRequest.getContextPath()+"/errorSesion.xhtml");
        /*}else {
			localHttpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "SESIÓN CADUCADA");
		}*/
    }

    /**
     * @param localHttpServletRequest
     * @return
     */
    private String limpiarUri(HttpServletRequest localHttpServletRequest) {
        System.out.println(localHttpServletRequest.getServletPath());
        return localHttpServletRequest.getServletPath();
    }

    /**
     *
     * Establece el Facescontex para poder referenciar al managebean de
     * autorizacion
     *
     * @param request
     * @param response
     * @return
     */
    protected FacesContext getFacesContext(HttpServletRequest request,
            HttpServletResponse response) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {

            FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder
                    .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                    .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle = lifecycleFactory
                    .getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            facesContext = contextFactory.getFacesContext(request.getSession()
                    .getServletContext(), request, response, lifecycle);

            // Set using our inner class
            InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

            // set a new viewRoot, otherwise context.getViewRoot returns null
            UIViewRoot view = facesContext.getApplication().getViewHandler()
                    .createView(facesContext, "");
            facesContext.setViewRoot(view);
        }
        return facesContext;
    }

    /**
     * @param facesContext
     * @return
     */
    protected Application getApplication(FacesContext facesContext) {
        return facesContext.getApplication();
    }

    /**
     * @param beanName
     * @param facesContext
     * @return
     */
    protected Object getManagedBean(String beanName, FacesContext facesContext) {
        //getApplication(facesContext).getVariableResolver().resolveVariable(facesContext, beanName);
        return getApplication(facesContext).getELResolver().getValue(facesContext.getELContext(), null, beanName);
    }

    private abstract static class InnerFacesContext extends FacesContext {

        protected static void setFacesContextAsCurrentInstance(
                FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T> T getBeanInstance(BeanManager bm_, Class<T> class_) {
        Bean<T> bean = (Bean<T>) bm_.getBeans(class_).iterator().next();
        CreationalContext<T> ctx = bm_.createCreationalContext(bean);
        //bm_.getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, class_);
        T object = (T) bm_.getReference(bean, class_, ctx);
        return object;
    }

}
