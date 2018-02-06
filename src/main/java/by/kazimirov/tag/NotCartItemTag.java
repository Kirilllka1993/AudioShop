package by.kazimirov.tag;

import by.kazimirov.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 *
 */
public class NotCartItemTag extends TagSupport {
    private Track test;
    private List<Track> items;

    public void setTest(Track test) {
        this.test = test;
    }

    public void setItems(List<Track> items) {
        this.items = items;
    }

    @Override
    public int doStartTag() throws JspException {
        return !items.contains(test) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}
