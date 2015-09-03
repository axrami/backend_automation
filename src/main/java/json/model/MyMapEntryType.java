package json.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by andrew on 9/2/15.
 */
public class MyMapEntryType {

    @XmlAttribute
    public Integer key;

    @XmlValue
    public String value;
}
