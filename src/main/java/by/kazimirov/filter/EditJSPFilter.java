package by.kazimirov.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebFilter(filterName = "EditJSPFilter", urlPatterns = {"/jsp/admin/edit.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class EditJSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
       /* try {
            TrackLogic trackLogic = new TrackLogic();
            AlbumLogic albumLogic = new AlbumLogic();
            GenreLogic genreLogic = new GenreLogic();
            ArtistLogic artistLogic = new ArtistLogic();
            request.setAttribute(CommandConstants.ATTR_TRACK_LIST, trackLogic.loadAllTracks());
            request.setAttribute(CommandConstants.ATTR_ALBUM_LIST, albumLogic.loadAllAlbums());
            request.setAttribute(CommandConstants.ATTR_GENRE_LIST, genreLogic.loadAllGenres());
            request.setAttribute(CommandConstants.ATTR_ARTIST_LIST, artistLogic.loadAllArtists());
        } catch (LogicException e) {
            AbstractServletCommand.handleDBError(e, request, response);
            return;
        }*/

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
