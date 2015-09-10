import json.JsonMarshaller;
import json.model.AppSettings;
import model.LPMobileHttpResponse;
import model.TestReporter;
import networking.VisitHandler;
import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.Session;
import service.chat.ChatHandler;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by andrew on 8/24/15.
 */
public class SessionTest extends LPTest {

    private static JsonMarshaller jsonMarshaller = new JsonMarshaller();

    private static JSONObject testResults = new JSONObject();
    private Logger logger = org.slf4j.LoggerFactory.getLogger("SessionTest");
    private TestReporter reporter = new TestReporter();

    @Test(dataProvider = "platformPicker", threadPoolSize = 5, invocationCount = 2, timeOut = 10000)
    public void beginVisit(AppSettings appSettings) {
        Session session = new Session(appSettings, null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        Assert.assertEquals(visit.response.isSuccess(), true);
        LPMobileHttpResponse result = visit.response;
        reporter.logResult(result);
    }

    @Test(dataProvider = "platformPicker", threadPoolSize = 5, invocationCount = 2, timeOut = 1000000)
    public void continueVisit(AppSettings appSettings) {
        try {
            Session session = new Session(appSettings, null);
            setSessionConfig(session);
            VisitHandler visit = session.beginVisit();
            Assert.assertEquals(visit.response.isSuccess(), true);
            Thread.sleep(15000);
            LPMobileHttpResponse contineueResponse = visit.continueVisit();
            Assert.assertEquals(contineueResponse.isSuccess(), true);
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "platformPicker", threadPoolSize = 200, invocationCount = 200, timeOut = 10000)
    public void beginChatAgentEnd(AppSettings appSettings) {
        Session session = new Session(appSettings, null);
        setSessionConfig(session);
        VisitHandler visit = session.beginVisit();
        ChatHandler chat = session.beginChat();
        try {
            Assert.assertEquals(chat.sendLinePostRequest("hello").isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest("hello").isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest("hello").isSuccess(), true);
            Assert.assertEquals(chat.sendLinePostRequest("end").isSuccess(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "platformPicker")
    public void visitAdvisories(AppSettings appSettings) {
        try {
            Session session = new Session(appSettings, null);
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "platformPicker")
    public void chatSendFeedback(AppSettings appSettings) {
        try {
            Session session = new Session(appSettings, null);
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
            Assert.assertEquals(chat.sendFeedbackPostRequest("ramirez.andrew989@gmail.com", "I thought the chat was great!").isSuccess(), true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendCustomVars() {
        try {
            Session session = new Session(buildAndroidEnv(), null);
            setSessionConfig(session);
            VisitHandler visit = session.beginVisit();
            ChatHandler chat = session.beginChat();
            chat.sendCustomVarsPostRequest("Andrew", "Ramirez");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
