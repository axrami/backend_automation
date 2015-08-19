package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 8/18/15.
 */
@XmlRootElement
public class CustomVar {
    String variable;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
