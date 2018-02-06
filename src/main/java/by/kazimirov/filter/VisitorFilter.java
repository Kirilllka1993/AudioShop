package by.kazimirov.filter;

import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Visitor;
import by.kazimirov.manager.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 */
@WebFilter(filterName = "VisitorFilter", urlPatterns = {"/*"})
public class VisitorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Visitor visitor = (Visitor)request.getSession().getAttribute(ControllerConstants.VISITOR_KEY);
        if (visitor == null) {
            visitor = new Visitor();
            visitor.setRole(Visitor.Role.GUEST);
            visitor.setLocale(ControllerConstants.DEFAULT_LOCALE);
            visitor.setCurrentPage(ConfigurationManager.getProperty(ControllerConstants.PAGE_INDEX));
            request.getSession().setAttribute(ControllerConstants.VISITOR_KEY, visitor);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
