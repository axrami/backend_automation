import json.model.AppSettings;
import networking.VisitHandler;
import org.testng.Assert;
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
        appSettings.setPlatform("Apple iOS");
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
        appSettings.setRequest_visitor_id(true);

        return appSettings;
    }

    public AppSettings buildWebEnv() {
        AppSettings appSettings = new AppSettings();
        appSettings.setApp_id("5df4277b");
        appSettings.setPlatform("Web");
        appSettings.setDevice_id("1b69aff5-a0d0-29c8-d02e-13428ba250e8");
        appSettings.setLanguage("en");
        appSettings.setBranding_md5("9bce1d24bf82e79e01b71753ec5bf6b4");
        appSettings.setDevice_type("Mozilla\\/5.0 (Linux; Android 4.4.2; SM-G900H Build\\/KOT49H) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/44.0.2403.133 Mobile Safari\\/537.36");
        appSettings.setRequest_visitor_id(true);

        return appSettings;
    }

    public Session setSessionConfig(Session session) {
        session.setConfig("staging", 1, true);
        return session;
    }

    @Test
    public void beginIosVisit() {
        Session session = new Session(buildIosEnv(), null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }

    @Test
    public void beginIosChat() {
        Session session = new Session(buildIosEnv(), null);
        setSessionConfig(session);

    }

    @Test
    public void beginWebVisit() {
        Session session = new Session(buildWebEnv(), null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }

    @Test
    public void beginWebChat() {
        Session session = new Session(buildIosEnv(), null);
        setSessionConfig(session);

    }


    @Test
    public void beginAndroidVisit() {
        Session session = new Session(buildAndroidEnv(), null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }

    @Test
    public void beginAndroidChat() {
        Session session = new Session(buildAndroidEnv(), null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        ChatHandler chat = session.beginChat();
        try {
            Assert.assertEquals(chat.sendLinePostRequest().isSuccess(), true);
            Thread.sleep(7000);
            Assert.assertEquals(chat.sendLinePostRequest().isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest().isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest().isSuccess(), true);
            Assert.assertEquals(chat.sendOutroPostRequest("").isSuccess(), true);
        } catch (IOException | InterruptedException e ) {
            e.printStackTrace();
        }

    }



}
