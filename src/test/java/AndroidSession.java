import json.model.AppSettings;
import networking.VisitHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.Session;
import service.chat.ChatHandler;

import java.io.IOException;

/**
 * Created by andrew on 8/25/15.
 */
public class AndroidSession {

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

    public Session setSessionConfig(Session session) {
        session.setConfig("staging", 1, true);
        return session;
    }

    @Test
    public void beginAndroidVisit() {
        Session session = new Session(buildAndroidEnv(), null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
    }

    @Test
    public void continueVisit() {
        try {
            Session session = new Session(buildAndroidEnv(), null);
            setSessionConfig(session);
            VisitHandler visit = session.beginVisit();
            Assert.assertEquals(visit.response.isSuccess(), true);
            Thread.sleep(session.visitIntroResponse.getNext_interval() * 1000);
            Assert.assertEquals(visit.continueVisit().isSuccess(), true);
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
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

    @Test
    public void visitAdvisories() {
        try {
            Session session = new Session(buildAndroidEnv(), null);
            setSessionConfig(session);
            VisitHandler visit = session.beginVisit();
            ChatHandler chat = session.beginChat();
            Assert.assertEquals(chat.sendAdvisoryPostRequest("app_foregrounded").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("chat_up").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("typing_start").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("typing_stop").isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest("Hello World!").isSuccess(), true);
            Thread.sleep(2000);
            Assert.assertEquals(chat.sendLinePostRequest("end").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("chat_down").isSuccess(), true);
            Assert.assertEquals(visit.continueVisit().isSuccess(), true);
        } catch (IOException | InterruptedException e ) {
            e.printStackTrace();
        }
    }

    @Test
    public void chatSendFeedback() {
        try {
            Session session = new Session(buildAndroidEnv(), null);
            setSessionConfig(session);
            VisitHandler visit = session.beginVisit();
            ChatHandler chat = session.beginChat();
            Assert.assertEquals(chat.sendAdvisoryPostRequest("app_foregrounded").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("chat_up").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("typing_start").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("typing_stop").isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest("Hello World!").isSuccess(), true);
            Thread.sleep(2000);
            Assert.assertEquals(chat.sendLinePostRequest("end").isSuccess(), true);
            Assert.assertEquals(chat.sendAdvisoryPostRequest("chat_down").isSuccess(), true);
            Assert.assertEquals(visit.continueVisit().isSuccess(), true);
            Assert.assertEquals(chat.sendFeedbackPostRequest("ramirez.andrew989@gmail.com" , "I thought the chat was great!").isSuccess() , true);
        } catch (IOException | InterruptedException e ) {
            e.printStackTrace();
        }
    }



}
