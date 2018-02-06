package by.kazimirov.filter;

import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.entity.Artist;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.ArtistLogic;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 */
@WebFilter(filterName = "CatalogArtistsJSPFilter", urlPatterns = {"/jsp/user/catalog_artists.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CatalogArtistsJSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            ArtistLogic artistLogic = new ArtistLogic();
            List<Artist> artistList = artistLogic.loadAllArtists();
            request.getSession().setAttribute(CommandConstants.ATTR_ARTIST_LIST, artistList);
        } catch (LogicException e) {
            AbstractServletCommand.handleDBError(e, request, response);
            return;
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
