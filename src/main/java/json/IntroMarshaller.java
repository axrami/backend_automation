package json;

import json.model.Employee;
import json.model.Intro;
import model.LPMobileVisit;
import org.apache.http.HttpResponse;
import org.apache.http.conn.BasicManagedEntity;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;


/**
 * Created by andrew on 7/30/15.
 */

public class IntroMarshaller {
    public Logger logger = LoggerFactory.getLogger("IntroMarshaller");

    public void unmarshalJson(HttpResponse httpResponse, LPMobileVisit visit) {
        logger.debug("unmarshal attempt");
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

                JAXBContext jc = JAXBContextFactory.createContext(new Class[]{Intro.class}, null);
                Unmarshaller um = jc.createUnmarshaller();
                um.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
                um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
                Intro intro = um.unmarshal(responseJson, Intro.class).getValue();
                visit.setIntro(intro);

//                logger.debug("Unmarshalled " + intro.());
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
