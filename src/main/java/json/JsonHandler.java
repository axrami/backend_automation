package json;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by andrew on 8/11/15.
 */
public class JsonHandler {
//    public Logger logger = LoggerFactory.getLogger("JsonHandler");
//
//    public <O> String marshalJson() throws JAXBException {
//        JAXBContext js = JAXBContextFactory.createContext(new Class[]{  }, null);
//        Marshaller marshaller = js.createMarshaller();
//        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
//        StringWriter sw = new StringWriter();
//        marshaller.marshal(O, sw);
//        return sw.toString();
//    }
}
