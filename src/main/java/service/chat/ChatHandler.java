package service.chat;

import json.JsonMarshaller;
import json.model.*;
import model.*;
import json.model.LPMobileEnvironment;
import networking.chat.IntroChatResponse;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 7/10/15.
 */
public class ChatHandler {

    boolean chatConnected = false;
    ChatConnectionHandler chatConnectionHandler = new ChatConnectionHandler();
    LPMobileChat chat = new LPMobileChat();
    LPMobileEnvironment env;
    Visitor visitor;
    IntroChatResponse introChatResponse;
    JsonMarshaller jsonMarshaller = new JsonMarshaller();
    VisitIntroResponse visitIntroResponse;
    AppSettings appSettings;
    public Logger logger = LoggerFactory.getLogger("ChatHandler");

    public ChatHandler createConnection(LPMobileEnvironment env, AppSettings appSettings, VisitIntroResponse visitIntroResponse, Visitor visitor) {
        this.env = env;
        this.visitIntroResponse = visitIntroResponse;
        this.visitor = visitor;
        this.appSettings = appSettings;
        this.introChatResponse = chatConnectionHandler.createChatConnection(env, appSettings, visitIntroResponse);
        return this;
    }

    // Example: {"text":"hello"}
    public HttpResponse sendLinePostRequest() throws IOException {
        try {
            Line line = new Line();
            line.setText("Hello");
            String postBody = jsonMarshaller.marshalObj(line, Class.forName("json.model.Line"));
            return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "line/" + introChatResponse.getEngagementId());
        } catch (JAXBException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Body not parsed
    public HttpResponse sendOutroPostRequest(VisitIntroResponse visitIntroResponse, String postBody) throws IOException {
        Outro outro = new Outro();
        outro.setBody(postBody);
        try {
            postBody = jsonMarshaller.marshalObj(outro, Class.forName("json.model.Outro"));
            return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "outro/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // Example: {“prechat”:survey,"postchat":survey,"offline":survey}
    public HttpResponse sendSurveyPostRequest(VisitIntroResponse visitIntroResponse, String postBody, IntroChatResponse intro) throws IOException{
        Survey survey = new Survey();
        return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, intro, "survey/" + intro.getEngagementId());
    }

    // Example: {"email_address":"visitor@email.com","message":"feedback text"}
    public HttpResponse sendFeedbackPostRequest(VisitIntroResponse visitIntroResponse, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "feedback/" + introChatResponse.getEngagementId());
    }

    // Example: {"capabilities":["show_leavemessage","capability2",”capability3”]}
    public HttpResponse sendCapabilitiesPostRequest(VisitIntroResponse visitIntroResponse, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "capabilities/" + introChatResponse.getEngagementId());
    }

    // Example (to send chat history to email): {"email_addresses":["email1","email2"]}
    // Example (to send chat history to SSE channel): {}
    public HttpResponse sendChatHistoryPostRequest(VisitIntroResponse visitIntroResponse, String email) throws IOException {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setEmail(email);
        try {
            String postBody = jsonMarshaller.marshalObj(chatHistory, Class.forName("json.model.ChatHistory"));
            return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "chat_history/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // Example: {“variable”:value}
    public HttpResponse sendCustomVarsPostRequest(VisitIntroResponse visitIntroResponse, String name, String value) throws IOException {
        Map<String, String> var= new HashMap<>();
        var.put(name, value);
        CustomVariable customVariable = new CustomVariable(var);
        try {
            String postBody = jsonMarshaller.marshalObj(customVariable, Class.forName("json.model.CustomVariable"));
            return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "custom_vars/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Example: {"action":"typing_start"}
    public HttpResponse sendAdvisoryPostRequest(VisitIntroResponse visitIntroResponse, String action) throws IOException {
        Advisory advisory = new Advisory();
        advisory.setAction(action);
        try {
            String postBody = jsonMarshaller.marshalObj(advisory, Class.forName("json.model.Advisory"));
            return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "advisory/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Body: binary
    public HttpResponse sendScreenShotPostRequest(VisitIntroResponse visitIntroResponse, String postBody) throws IOException {
        return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "screenshot/" + introChatResponse.getEngagementId());
    }

    //Example: {"permission":"revoked", “asset”:”screenshare”} , {"permission":"granted", “asset”:”screenshare”}
    public HttpResponse sendPermissionPostRequest(VisitIntroResponse visitIntroResponse, String perm, String asset) throws IOException {
        Permission permission = new Permission();
        permission.setPermission(perm);
        permission.setAsset(asset);
        try {
            String postBody = jsonMarshaller.marshalObj(permission, Class.forName("json.model.Permission"));
            return chatConnectionHandler.sendPostRequest(visitIntroResponse, postBody, introChatResponse, "permission/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LPMobileEnvironment getEnv() {
        return env;
    }

    public void setEnv(LPMobileEnvironment env) {
        this.env = env;
    }

    public VisitIntroResponse getVisit() {
        return visitIntroResponse;
    }

    public void setVisit(VisitIntroResponse visitIntroResponse) {
        this.visitIntroResponse = visitIntroResponse;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
