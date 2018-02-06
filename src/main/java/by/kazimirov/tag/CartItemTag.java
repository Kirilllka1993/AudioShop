package by.kazimirov.tag;

import by.kazimirov.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 *
 */
public class CartItemTag extends TagSupport {
    private Track test;
    private List<Track> items;

    public void setTest(Track track) {
        this.test = track;
    }

    public void setItems(List<Track> cartItems) {
        this.items = cartItems;
    }

    @Override
    public int doStartTag() throws JspException {
        return items.contains(test) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}
