package by.kazimirov.filter;

import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Visitor;
import by.kazimirov.util.URIAnalyzer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 */
@WebFilter(filterName = "CurrentPageFilter", urlPatterns = {"/jsp/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class CurrentPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Visitor visitor = (Visitor)request.getSession().getAttribute(ControllerConstants.VISITOR_KEY);
        String uri = URIAnalyzer.cleanURI(request.getRequestURI().substring(request.getContextPath().length()));
        if (URIAnalyzer.isPageURI(uri)) {
            visitor.setCurrentPage(uri);
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
