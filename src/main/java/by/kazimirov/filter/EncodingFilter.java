package by.kazimirov.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 *
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestEncoding = servletRequest.getCharacterEncoding();
        String responseEncoding = servletResponse.getCharacterEncoding();
        if (this.encoding != null && !this.encoding.equalsIgnoreCase(requestEncoding)) {
            servletRequest.setCharacterEncoding(this.encoding);
        }
        if (this.encoding != null && !this.encoding.equalsIgnoreCase(responseEncoding)) {
            servletResponse.setCharacterEncoding(this.encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
