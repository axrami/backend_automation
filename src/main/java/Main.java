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
        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(3);
        Runnable task = () -> {
            Session session = new Session();
            session.beginVisit();
        };
        int intDelay = 0;
        int period = 5000;
        executor2.scheduleAtFixedRate(task, intDelay, period, TimeUnit.MILLISECONDS);
    }
}



