package json;

import org.apache.http.HttpResponse;
import org.apache.http.conn.BasicManagedEntity;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;


/**
 * Created by andrew on 7/30/15.
 */

public class IntroMarshaller {
    public Logger logger = LoggerFactory.getLogger("IntroMarshaller");
    public Object unmarshalJson(HttpResponse httpResponse, Class clazz) {
        try {
            if (httpResponse.getEntity() instanceof BasicManagedEntity) {
                BasicManagedEntity entity = (BasicManagedEntity) httpResponse.getEntity();
                StringBuilder inputStr = new StringBuilder(1000);
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf8"), 8192);
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    inputStr.append(currentLine);
                }
                logger.debug("<Response>  " + inputStr.toString());
                StreamSource responseJson = new StreamSource(new StringReader("{\"response\":" + inputStr.toString() + "}"));
                JAXBContext jc = JAXBContextFactory.createContext(new Class[]{clazz}, null);
                Unmarshaller um = jc.createUnmarshaller();
                um.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
                um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
                return um.unmarshal(responseJson, clazz).getValue();
            }
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String marshalObj(Object obj, Class clazz) throws JAXBException {
            JAXBContext jc = JAXBContextFactory.createContext(new Class[]{clazz}, null);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
            StringWriter sw = new StringWriter();
            marshaller.marshal(obj, sw);
            return sw.toString();
    }
}
