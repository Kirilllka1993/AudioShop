package by.kazimirov.filter;

import by.kazimirov.ajax.BiTuple;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.entity.Account;
import by.kazimirov.entity.Comment;
import by.kazimirov.entity.Track;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@WebFilter(filterName = "TrackJSPFilter", urlPatterns = {"/jsp/user/track.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class TrackJSPFilter implements Filter {

    private static final String ATTR_CURRENT_TRACK = "currentTrack";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            CommentLogic commentLogic = new CommentLogic();
            AccountLogic accountLogic = new AccountLogic();
            List<BiTuple<Comment, Account>> data = new LinkedList<>();
            Track track = (Track) request.getSession().getAttribute(ATTR_CURRENT_TRACK);
            List<Comment> comments  = commentLogic.loadAllTrackComments(track.getTrackId());
            for(Comment comment: comments) {
                data.add(new BiTuple<>(comment, accountLogic.loadAccount(comment.getAccountId())));
            }
            TrackLogic trackLogic = new TrackLogic();
            AlbumLogic albumLogic = new AlbumLogic();
            GenreLogic genreLogic = new GenreLogic();
            ArtistLogic artistLogic = new ArtistLogic();
            request.setAttribute(CommandConstants.ATTR_TRACK_LIST, trackLogic.loadAllTracks());
            request.setAttribute(CommandConstants.ATTR_ALBUM_LIST, albumLogic.loadAllAlbums());
            request.setAttribute(CommandConstants.ATTR_GENRE_LIST, genreLogic.loadAllGenres());
            request.setAttribute(CommandConstants.ATTR_ARTIST_LIST, artistLogic.loadAllArtists());
            request.setAttribute(CommandConstants.ATTR_TRACK_COMMENT_LIST, data);
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
