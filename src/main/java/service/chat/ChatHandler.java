package service.chat;

import json.JsonGenerator;
import json.JsonParser;
import json.model.Line;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Logger logger = LoggerFactory.getLogger("ChatHandler");

    public void createConnection(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        this.env = env;
        this.visit = visit;
        this.visitor = visitor;
        this.introChatResponse = chatConnectionHandler.createChatConnection(env, visit,visitor, chat);
    }

// Old static way
//    public void sendLine() {
//        try {
//            String suffix = "line/";
//            String postBody = JsonGenerator.generateChatLineReqeust("Hello");
//            HttpResponse response = chatConnectionHandler.sendPost(visit, visitor, postBody, introChatResponse, suffix);
//            logger.debug("<sendLine> response " + response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // Example: {"text":"hello"}
    public HttpResponse sendLinePostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        Line line = new Line();
        line.setText(postBody);
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "line/" + intro.getEngagementId());
    }

    // Body not parsed
    public HttpResponse sendOutroPostReqeust(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "outro/" + intro.getEngagementId());
    }

    // Example: {“prechat”:survey,"postchat":survey,"offline":survey}
    public HttpResponse sendSurveyPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException{
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "survey/" + intro.getEngagementId());
    }

    // Example: {"email_address":"visitor@email.com","message":"feedback text"}
    public HttpResponse sendFeedbackPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "feedback/" + intro.getEngagementId());
    }

    // Example: {"capabilities":["show_leavemessage","capability2",”capability3”]}
    public HttpResponse sendCapabilitiesPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "capabilities/" + intro.getEngagementId());
    }

    // Example (to send chat history to email): {"email_addresses":["email1","email2"]}
    // Example (to send chat history to SSE channel): {}
    public HttpResponse sendChatHistoryPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "chat_history/" + intro.getEngagementId());
    }

    // Example: {“variable”:value}
    public HttpResponse sendCustomVarsPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "custom_vars/" + intro.getEngagementId());
    }

    // Example: {"action":"typing_start"}
    public HttpResponse sendAdvisoryPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "advisory/" + intro.getEngagementId());
    }

    // Body: binary
    public HttpResponse sendScreenShotPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "screenshot/" + intro.getEngagementId());
    }

    //Example: {"permission":"revoked", “asset”:”screenshare”} , {"permission":"granted", “asset”:”screenshare”}
    public HttpResponse sendPermissionPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "permission/" + intro.getEngagementId());
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
