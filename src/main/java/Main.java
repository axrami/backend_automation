import json.JsonMarshaller;
import json.model.AppSettings;
import model.SetEnvironment;
import org.apache.http.HttpResponse;
import service.Session;
import service.chat.ChatHandler;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
        try {
            JsonMarshaller marshaller = new JsonMarshaller();
            AppSettings appsettings = SetEnvironment.createAppSettings();
            System.out.println(marshaller.marshalObj(appsettings, Class.forName("json.model.AppSettings")));
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }

    }

}

