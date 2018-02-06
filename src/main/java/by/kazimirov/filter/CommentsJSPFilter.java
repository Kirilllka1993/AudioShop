package by.kazimirov.filter;

import by.kazimirov.ajax.BiTuple;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.entity.Account;
import by.kazimirov.entity.Comment;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.AccountLogic;
import by.kazimirov.logic.CommentLogic;

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
@WebFilter(filterName = "CommentsJSPFilter", urlPatterns = {"/jsp/admin/comments.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CommentsJSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            CommentLogic commentLogic = new CommentLogic();
            AccountLogic accountLogic = new AccountLogic();
            List<BiTuple<Comment, Account>> data = new LinkedList<>();
            List<Comment> comments = commentLogic.loadAllComments();
            for (Comment comment : comments) {
                data.add(new BiTuple<>(comment, accountLogic.loadAccount(comment.getAccountId())));
            }
            request.setAttribute(CommandConstants.ATTR_COMMENT_LIST, data);
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
