package controller.Security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "/AccessControlFilter", urlPatterns = "/*")
public class AccessControlFilter extends HttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

        boolean isLogged = (httpServletRequest.getSession().getAttribute("Utente") != null);
        String path = httpServletRequest.getServletPath();
        System.out.println(path);

        if (path.contains("AreaUtente.jsp") && !isLogged){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login.jsp");
            return; // Interrompe l'esecuzione del filtro
        }

        if ((path.contains("Login.jsp") || path.contains("Registrazione.jsp")) && isLogged){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.jsp");
            return; // Interrompe l'esecuzione del filtro
        }

        if (path.contains("Ordine.jsp") && !isLogged) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.jsp");
            return; // Interrompe l'esecuzione del filtro
        }

        chain.doFilter(req, res);
    }
}
