package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 8/12/15.
 */
@XmlRootElement
public class Capabilities {
    String[] caps;

    public String[] getCaps() {
        return caps;
    }

    public void setCaps(String[] caps) {
        this.caps = caps;
    }
}
