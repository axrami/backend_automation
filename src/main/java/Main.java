import com.sun.tools.javac.jvm.Gen;
import json.JsonMarshaller;
import json.model.AppSettings;
import junit.framework.Assert;
import model.SetEnvironment;
import networking.VisitHandler;
import org.apache.http.HttpResponse;
import service.Generator;
import service.Session;
import service.chat.ChatHandler;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
        Generator gen = new Generator();
        ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(50);


        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(300);
        Runnable task = () -> {
                Session session = new Session(buildAndroidEnv(), null);
                session.setConfig("staging", 1, true);
                VisitHandler visit = session.beginVisit();
        };
//        Runnable task1 = () -> {
//            try {
//                Session session = new Session(buildAndroidEnv(), null);
//                session.setConfig("staging", 1, true);
//                VisitHandler visit = session.beginVisit();
//                ChatHandler chat = session.beginChat();
//                chat.sendLinePostRequest("Hello");
//                chat.sendLinePostRequest("Hello");
//                chat.sendLinePostRequest("end");
//            } catch (IOException e ) {
//                e.printStackTrace();
//            }
//        };
        int intDelay = 0;
        int period = 5000;
        executor2.scheduleAtFixedRate(task, intDelay, 1, TimeUnit.MILLISECONDS);
//        executor1.scheduleAtFixedRate(task1, intDelay, 5, TimeUnit.MILLISECONDS);
    }

    public static AppSettings buildAndroidEnv() {
        AppSettings appSettings = new AppSettings();
        appSettings.setApp_id("com.liveperson.mobile.ecosmart-P36511428");
        appSettings.setLanguage("en");
        appSettings.setPlatform("Android");
        appSettings.setBranding_md5("e3fa6750a0c2e557342831924e6d39d4");
        appSettings.setSkill("mobile");
        appSettings.setSite_id("P36511428");
        appSettings.setRequest_visitor_id(true);

        return appSettings;

    }
}



