import json.model.AppSettings;
import json.model.LPMobileEnvironment;
import networking.VisitHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Session;
import service.chat.ChatHandler;

import java.io.IOException;

/**
 * Created by andrew on 8/24/15.
 */
public class SessionTest {

    public AppSettings buildIosEnv() {
        AppSettings appSettings = new AppSettings();
        appSettings.setApp_id("io.look.sample-P36511428");
        appSettings.setVersion("1.1.0");
        appSettings.setSdk_version("0.1.1");
        appSettings.setLocale("en_US");
        appSettings.setDevice_type("x86_64");
        appSettings.setPlatform_version("8.4");
        appSettings.setPlateform_release("Apple iOS");
//        appSettings.setRequest_visitor_id(true);
        appSettings.setLanguage("en");
        appSettings.setStrings_hash("e8d2cb478b5411d43b5570ca0369bcb9"); // Default hash
//        appSettings.setApp_foregrounded(false);
        appSettings.setDevice_id("C4774032-3CAA-43BA-905C-8DAC769846F8");
        appSettings.setBranding_md5("8c8e4cf75e84221da289edf4d3e1730c"); // Default branding
        appSettings.setSkill("mobile");
        appSettings.setSite_id("P36511428");

        return appSettings;
    }

    public AppSettings buildAndroidEnv() {
        AppSettings appSettings = new AppSettings();
        appSettings.setApp_id("com.liveperson.mobile.ecosmart-P36511428");
        appSettings.setLanguage("en");
        appSettings.setPlatform("Android");
        appSettings.setBranding_md5("e3fa6750a0c2e557342831924e6d39d4");
        appSettings.setSkill("mobile");
        appSettings.setSite_id("P36511428");


        return appSettings;
    }

    public AppSettings buildWebEnv() {
        AppSettings appSettings = new AppSettings();

        return appSettings;
    }

    @Test
    public void beginIosVisit() {
        Session session = new Session(buildIosEnv(), null);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }


    @Test
    public void beginWebVisit() {
        Session session = new Session(buildWebEnv(), null);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }

    // Android Environment

    @Test
    public void beginAndroidVisit() {
        Session session = new Session(buildAndroidEnv(), null);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }

    @Test
    public void beginChat() {
        Session session = new Session(buildAndroidEnv(), null);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
        ChatHandler chat = session.beginChat();
        try {
            System.out.println("Is Success?  " + chat.sendLinePostRequest("Hello").isSuccess());
        } catch (IOException e ) {
        }

    }



}
