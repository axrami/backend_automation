package service.chat;

import de.svenjacobs.loremipsum.LoremIpsum;
import json.IntroMarshaller;
import json.model.Line;
import model.*;
import networking.chat.IntroChatResponse;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;

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
    LoremIpsum lorem = new LoremIpsum();
    IntroMarshaller jsonMarshaller = new IntroMarshaller();
    public Logger logger = LoggerFactory.getLogger("ChatHandler");

    public void createConnection(LPMobileEnvironment env, LPMobileVisit visit, Visitor visitor) {
        this.env = env;
        this.visit = visit;
        this.visitor = visitor;
        this.introChatResponse = chatConnectionHandler.createChatConnection(env, visit,visitor, chat);
    }

    // Example: {"text":"hello"}
    public HttpResponse sendLinePostRequest() throws IOException {
        try {
            Line line = new Line();
            line.setText("Hello");
            String postBody = jsonMarshaller.marshalObj(line, Class.forName("json.model.Line"));
            return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "line/" + introChatResponse.getEngagementId());
        } catch (JAXBException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Body not parsed
    public HttpResponse sendOutroPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        if (postBody == null) {postBody = "";}
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "outro/" + introChatResponse.getEngagementId());
    }

    // Example: {“prechat”:survey,"postchat":survey,"offline":survey}
    public HttpResponse sendSurveyPostRequest(LPMobileVisit visit, String postBody, IntroChatResponse intro) throws IOException{
        return chatConnectionHandler.sendPostRequest(visit, postBody, intro, "survey/" + intro.getEngagementId());
    }

    // Example: {"email_address":"visitor@email.com","message":"feedback text"}
    public HttpResponse sendFeedbackPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "feedback/" + introChatResponse.getEngagementId());
    }

    // Example: {"capabilities":["show_leavemessage","capability2",”capability3”]}
    public HttpResponse sendCapabilitiesPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "capabilities/" + introChatResponse.getEngagementId());
    }

    // Example (to send chat history to email): {"email_addresses":["email1","email2"]}
    // Example (to send chat history to SSE channel): {}
    public HttpResponse sendChatHistoryPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "chat_history/" + introChatResponse.getEngagementId());
    }

    // Example: {“variable”:value}
    public HttpResponse sendCustomVarsPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "custom_vars/" + introChatResponse.getEngagementId());
    }

    // Example: {"action":"typing_start"}
    public HttpResponse sendAdvisoryPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "advisory/" + introChatResponse.getEngagementId());
    }

    // Body: binary
    public HttpResponse sendScreenShotPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "screenshot/" + introChatResponse.getEngagementId());
    }

    //Example: {"permission":"revoked", “asset”:”screenshare”} , {"permission":"granted", “asset”:”screenshare”}
    public HttpResponse sendPermissionPostRequest(LPMobileVisit visit, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visit, postBody, introChatResponse, "permission/" + introChatResponse.getEngagementId());
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
