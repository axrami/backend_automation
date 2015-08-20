import org.testng.annotations.Test;
import service.Session;
import service.chat.ChatHandler;

import java.io.IOException;

/**
 * Created by andrew on 8/18/15.
 */
public class ChatHandlerTest {
    @Test
    public void beginChatSendOutro() {
        Session session = new Session();
        ChatHandler chat = session.beginChat();
        try {
            chat.sendLinePostRequest();
            chat.sendOutroPostRequest(session.getVisit(), null);
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    @Test
    public void sendCustomVars() {
        Session session = new Session();
        ChatHandler chat = session.beginChat();
        try {
            chat.sendLinePostRequest();
            chat.sendCustomVarsPostRequest(session.getVisit(), "Andrew" , "25");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
