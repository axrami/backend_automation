package json.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 8/18/15.
 */
@XmlRootElement
public class CustomVariable {
    Map<String, String> var;

    public CustomVariable() {
    }

    public CustomVariable(Map<String, String> var) {
        this.var = var;
    }

    public Map<String, String> getVar() {
        return this.var;
    }

    public void setVar(Map<String, String> var) {
        this.var = var;
    }
}
