import json.JsonMarshaller;
import json.model.Line;
import model.LPMobileEnvironment;
import org.junit.Test;
import service.Session;
import service.chat.ChatHandler;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by andrew on 7/23/15.
 */
public class SessionTest {

    public static LPMobileEnvironment createBaseEnv() {
        LPMobileEnvironment env = new LPMobileEnvironment();
        env.setAppID("com.liveperson.mobile.ecosmart-P36511428");
        env.setLanguage("en");
        env.setAccessibilityActive(false);
        env.setPlatform("Android");
        env.setBrandingHashStr("e3fa6750a0c2e557342831924e6d39d4");
        env.setSkillId("mobile");
        env.setLocalizedHashStr("38ceb8450c28f8adbe48ee71ae01e744");
        env.setDeviceID(UUID.randomUUID().toString());
        env.setCurrentSkill("mobile");
        env.setCurrentAccount("P36511428");
        env.setTzOffset("-25200");
        env.setLocale("en_US");

        return env;
    }

    @Test
    public void beginVisit() {
        Session session = new Session();
        session.beginVisit();
    }

    @Test
    public void beginChatSendMessage() {
        Session session = new Session();
        session.beginVisit(createBaseEnv());
    }

    @Test
    public void testMarshaller() {
        try {
            JsonMarshaller jsonMarshaller = new JsonMarshaller();
            Line line = new Line();
            line.setText("Hello");
            String postBody = jsonMarshaller.marshalObj(line, Class.forName("json.model.Line"));
            System.out.println(postBody);
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }

    }

    @Test
    public void lineTest() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                Session session = new Session();
                ChatHandler chat = session.beginChat();
                chat.sendLinePostRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        int intDelay = 1;
        int period = 3000;
        executorService.scheduleAtFixedRate(task, intDelay, period, TimeUnit.MILLISECONDS);

    }



}
