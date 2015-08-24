import org.testng.annotations.Test;
import service.Session;
import service.chat.ChatHandler;

import java.io.IOException;

/**
 * Created by andrew on 8/24/15.
 */
public class SessionTest {

    @Test
    public void beginVisit() {
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
