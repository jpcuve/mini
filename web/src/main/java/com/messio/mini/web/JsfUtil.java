package com.messio.mini.web;

import javax.activation.DataSource;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jpc on 11/4/14.
 */
public class JsfUtil {
    public static DataSource getViewDataSource(final FacesContext facesContext, final String viewId) throws IOException {
        final ExternalContext externalContext = facesContext.getExternalContext();
        final HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
        final BufferedHttpServletResponseWrapper res = new BufferedHttpServletResponseWrapper(viewId, httpServletResponse);
        externalContext.setResponse(res);
        final ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
        final UIViewRoot viewRoot = viewHandler.createView(facesContext, viewId);
        viewHandler.getViewDeclarationLanguage(facesContext, viewId).buildView(facesContext, viewRoot);
        viewHandler.renderView(facesContext, viewRoot);
        externalContext.setResponse(httpServletResponse);
        return res;
    }
}
