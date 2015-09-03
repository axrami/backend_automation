package json.model;

import json.MyMapAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 9/2/15.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Foo {
    @XmlJavaTypeAdapter(MyMapAdapter.class)
    Map<Integer, String> map = new HashMap<Integer, String>();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
