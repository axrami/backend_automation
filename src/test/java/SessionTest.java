import json.model.AppSettings;
import json.model.LPMobileEnvironment;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Session;
import service.chat.ChatHandler;

import java.io.IOException;

/**
 * Created by andrew on 8/24/15.
 */
public class SessionTest {

    public AppSettings definEnvironment() {
        AppSettings appSettings = new AppSettings();
        appSettings.setApp_id("com.liveperson.mobile.ecosmart-P36511428");
        appSettings.setPlatform("Android");
        appSettings.setDevice_id("286c5550ba06df78");
        appSettings.setLanguage("en");
        return appSettings;
    }

    @Test
    public void beginVisitWithDefaults() {
        Session session = new Session();
        session.beginVisit();
        ChatHandler chat = session.beginChat();
        try {
            chat.sendLinePostRequest();
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }


}
