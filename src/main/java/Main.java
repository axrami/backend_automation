import json.JsonMarshaller;
import json.model.AppSettings;
import model.SetEnvironment;
import networking.VisitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.LPMobileConfig;
import service.Session;
import service.chat.ChatHandler;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
//        Generator gen = new Generator();
        LPMobileConfig config = LPMobileConfig.getInstance();
        JsonMarshaller jsonMarshaller = new JsonMarshaller();
        Logger logger = LoggerFactory.getLogger("Visits");
        ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(50);
        AppSettings appSettings = SetEnvironment.createAppSettings();

//        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(100);
//        Runnable task = () -> {
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(config.getVisitDomain());
//                httppost.addHeader(new BasicHeader("Content-type", "application/json"));
//                httppost.addHeader(new BasicHeader("X-LivepersonMobile-Capabilities", "account-skills"));
//                String postBody = jsonMarshaller.marshalObj(appSettings, Class.forName("json.model.AppSettings"));
//                httppost.setEntity(new StringEntity(postBody));
//                HttpResponse httpResponse = httpclient.execute(httppost);
//                logger.debug(httpResponse.getStatusLine().getStatusCode() + "");
//            } catch (ClassNotFoundException | JAXBException | IOException e ) {
//                e.printStackTrace();
//            }
//        };
        Runnable task1 = () -> {
            try {
                Session session = new Session(buildAndroidEnv(), null);
                session.setConfig("staging", 1, true);
                VisitHandler visit = session.beginVisit();
                ChatHandler chat = session.beginChat();
                chat.sendLinePostRequest("Hello");
            } catch (IOException e ) {
                e.printStackTrace();
            }
        };
        int intDelay = 0;
        int period = 5000;
//        executor2.scheduleAtFixedRate(task, intDelay, 1, TimeUnit.MILLISECONDS);
        executor1.scheduleAtFixedRate(task1, intDelay, 5, TimeUnit.MILLISECONDS);
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



