package service.chat;

import json.JsonGenerator;
import json.JsonParser;
import model.*;
import networking.chat.IntroChatResponse;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import properties.LPMobileProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Handler;

/**
 * Created by andrew on 7/10/15.
 */
public class ChatHandler {

    public void createConnection(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        LPMobileChat chat = new LPMobileChat();
        ChatConnectionHandler chatConnectionHandler = new ChatConnectionHandler();
        chatConnectionHandler.createChatConnection(env, visit,visitor, chat);
    }



}
