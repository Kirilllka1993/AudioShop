package by.kazimirov.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 *
 */
public class EmptyListTag extends TagSupport{
    private int items;

    public void setItems(List<Object> itemsList) {
        this.items = itemsList.size();
    }

    @Override
    public int doStartTag() throws JspException {
        return items == 0 ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}
