package service.chat;

import model.LPMobileEnvironment;
import model.LPMobileVisit;
import model.SetEnvironment;
import properties.LPMobileProperties;

import java.util.logging.Handler;

/**
 * Created by andrew on 7/10/15.
 */
public class Chat {
    ChatManager chatManager = null;
    ChatConnectionHandler chatConnectionHandler = null;

    static String bundleId;
    String ChatDomain = LPMobileProperties.getChatStagDomain();
    LPMobileEnvironment env = SetEnvironment.createEnv();

    final int notificationId = 1212212;

    public void startNewChatSession(LPMobileVisit visit , LPMobileEnvironment env) {

    }

    public void beginSession() {

    }

}
