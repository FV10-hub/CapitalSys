package com.py.utils;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Edudzl
 */
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session= req.getSession();
        String usuario = session.getAttribute(ConstantesAplicacion.USUARIOSESSION) != null ? session.getAttribute(ConstantesAplicacion.USUARIOSESSION).toString() : null;
        String sPage= req.getRequestURL().toString();
        //ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = (req.getContextPath());//ctx.getContext()).getContextPath();
        System.out.println("paso por el filter "+usuario);
        if(usuario != null && sPage.contains("login.xhtml")){
            res.sendRedirect(ctxPath +"/inicio.xhtml");
        }else if (usuario == null && !sPage.contains("login.xhtml")){
            res.sendRedirect(ctxPath +"/login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        
    }
}