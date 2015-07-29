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

    boolean chatConnected = false;
    ChatConnectionHandler chatConnectionHandler = new ChatConnectionHandler();
    LPMobileChat chat = new LPMobileChat();
    LPMobileEnvironment env;
    LPMobileVisit visit;
    Visitor visitor;
    IntroChatResponse introChatResponse;

    public void createConnection(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        this.env = env;
        this.visit = visit;
        this.visitor = visitor;
        this.introChatResponse = chatConnectionHandler.createChatConnection(env, visit,visitor, chat);
    }

    public void sendLine() {
        String postBody = JsonGenerator.generateChatLineReqeust("Hello");
        chatConnectionHandler.sendLine(visit, visitor, introChatResponse, postBody);
    }

    public void sendOutro() {

    }

    public void sendFeedback(){

    }

    public void getChatHistory() {

    }

    public void sendAdvisory() {

    }

    public LPMobileEnvironment getEnv() {
        return env;
    }

    public void setEnv(LPMobileEnvironment env) {
        this.env = env;
    }

    public LPMobileVisit getVisit() {
        return visit;
    }

    public void setVisit(LPMobileVisit visit) {
        this.visit = visit;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
