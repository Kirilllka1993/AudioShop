package by.kazimirov.tag;

import by.kazimirov.entity.Visitor;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 */
public class  AdminTag extends TagSupport {
    private Visitor.Role role;

    public void setRole(Visitor.Role role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        return role == Visitor.Role.ADMIN ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}
