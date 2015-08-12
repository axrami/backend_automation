package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 8/10/15.
 */

@XmlRootElement
public class Line {
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
