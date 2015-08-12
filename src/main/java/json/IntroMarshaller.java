package json;

import json.model.*;
import model.LPMobileVisit;
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

    private static final Class[] classList = new Class[]{Intro.class, Line.class};

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
                um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
                Object unmarshalledObj = um.unmarshal(responseJson, clazz).getValue();
//                Intro intro = um.unmarshal(responseJson, Intro.class).getValue();
//                System.out.println(intro.getBranding_md5().isEmpty()); // true
//                visit.setIntro(intro);
                return unmarshalledObj;
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String marshalIntro(Object obj) throws JAXBException {
            JAXBContext jc = JAXBContextFactory.createContext(classList, null);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(obj, sw);
            return sw.toString();
    }

    public String marshalLine(Line line) throws JAXBException {
        JAXBContext jc = JAXBContextFactory.createContext(new Class[]{Intro.class}, null);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        StringWriter sw = new StringWriter();
        marshaller.marshal(line, sw);
        return sw.toString();
    }


}
