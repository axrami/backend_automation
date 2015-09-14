package service.chat;

import json.JsonMarshaller;
import json.model.*;
import model.*;
import networking.chat.IntroChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.LPMobileConfig;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 7/10/15.
 */
public class ChatHandler {

    boolean chatConnected = false;
    private ChatConnectionHandler chatConnectionHandler = new ChatConnectionHandler();
    private Visitor visitor;
    private IntroChatResponse introChatResponse;
    private JsonMarshaller jsonMarshaller = new JsonMarshaller();
    private VisitIntroResponse visitIntroResponse;
    private AppSettings appSettings;
    private LPMobileConfig config = LPMobileConfig.getInstance();
    private ChatIntro chatIntro = new ChatIntro();
    private Logger logger = LoggerFactory.getLogger("ChatHandler");

    // {"app_id":"P58916451","platform":"Web","skill":"mobile","visit_id":"0a982f4b9782cf1a0d80e7f318a86ca4"}
    public ChatHandler createConnection(AppSettings appSettings, VisitIntroResponse visitIntroResponse, Visitor visitor) {
        this.visitIntroResponse = visitIntroResponse;
        this.visitor = visitor;
        this.appSettings = appSettings;
        chatIntro.setApp_id(appSettings.getApp_id());
        chatIntro.setPlatform(appSettings.getPlatform());
        chatIntro.setSkill(appSettings.getSkill());
        chatIntro.setVisit_id(visitIntroResponse.getVisit_id());
        chatIntro.setSite_id(appSettings.getSite_id());
        chatIntro.setLanguage("en");
        this.introChatResponse = chatConnectionHandler.createChatConnection(chatIntro);
        return this;
    }

    // Example: {"text":"hello"}
    public LPMobileHttpResponse sendLinePostRequest() throws IOException {
        return sendLinePostRequest(null);
    }

    public LPMobileHttpResponse sendLinePostRequest(String text) throws IOException {
        try {
            Line line = new Line();
            if (text == null) {
                text = "";
            }
            line.setText(text);
            String postBody = jsonMarshaller.marshalObj(line, Class.forName("json.model.Line"));
            LPMobileHttpResponse response = chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "line/" + introChatResponse.getEngagementId());
            response.setRequestType("Line");
            response.setVisit_id(visitIntroResponse.getVisit_id());
            return response;

        } catch (JAXBException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Body not parsed
    public LPMobileHttpResponse sendOutroPostRequest() throws IOException {
        LPMobileHttpResponse result = sendOutroPostRequest("");
        result.setRequestType("Outro");
        return result;

    }

    public LPMobileHttpResponse sendOutroPostRequest(String postBody) throws IOException {
        Outro outro = new Outro();
        outro.setBody(postBody);
        String suffix = "outro/";
        LPMobileHttpResponse result;
        try {
            postBody = jsonMarshaller.marshalObj(outro, Class.forName("json.model.Outro"));
            result = chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, suffix + introChatResponse.getEngagementId());
            result.setRequestType("Outro");
            return result;
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }
        // (String url, int responseCode, String postBody, String httpResponse, String requestType)
//        return result = new LPMobileHttpResponse(null, null, postBody,introChatResponse,suffix)
        return null;
    }

    // Example: {“prechat”:survey,"postchat":survey,"offline":survey}
    public LPMobileHttpResponse sendSurveyPostRequest(VisitIntroResponse visitIntroResponse, String postBody, IntroChatResponse intro) throws IOException{
        Survey survey = new Survey();
        return chatConnectionHandler.postRequest(visitIntroResponse, postBody, intro, "survey/" + intro.getEngagementId());
    }

    // Example: {"email_address":"visitor@email.com","message":"feedback text"}
    public LPMobileHttpResponse sendFeedbackPostRequest(String email, String message) throws IOException {
        try {
            Feedback feedback = new Feedback();
            feedback.setEmail(email).setMessage(message);
            String postBody = jsonMarshaller.marshalObj(feedback, Class.forName("json.model.Feedback"));
            LPMobileHttpResponse result = chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "feedback/" + introChatResponse.getEngagementId());
            result.setRequestType("Feedback");
            return result;
        } catch (JAXBException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // Example: {"capabilities":["show_leavemessage","capability2",”capability3”]}
    public LPMobileHttpResponse sendCapabilitiesPostRequest(VisitIntroResponse visitIntroResponse, String postBody) throws IOException {
        return chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "capabilities/" + introChatResponse.getEngagementId());
    }

    // Example (to send chat history to email): {"email_addresses":["email1","email2"]}
    // Example (to send chat history to SSE channel): {}
    public LPMobileHttpResponse sendChatHistoryPostRequest(VisitIntroResponse visitIntroResponse, String email) throws IOException {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setEmail(email);
        try {
            String postBody = jsonMarshaller.marshalObj(chatHistory, Class.forName("json.model.ChatHistory"));
            return chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "chat_history/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e ) {
            e.printStackTrace();
        }
        return null;
    }

    // Example: {“variable”:value}
    public LPMobileHttpResponse sendCustomVarsPostRequest(String name, String value) throws IOException {
        Map<String, String> var= new HashMap<>();
        var.put(name, value);
        CustomVariable customVariable = new CustomVariable(var);
        try {
            String postBody = jsonMarshaller.marshalObj(customVariable, Class.forName("json.model.CustomVariable"));
            logger.debug("<CUSTOMEVAR VALUE>" + postBody);
            return chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "custom_vars/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Example: {"action":"typing_start"}
    public LPMobileHttpResponse sendAdvisoryPostRequest(String action) throws IOException {
        Advisory advisory = new Advisory();
        advisory.setAction(action);
        try {
            String postBody = jsonMarshaller.marshalObj(advisory, Class.forName("json.model.Advisory"));
            LPMobileHttpResponse result = chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "advisory/" + introChatResponse.getEngagementId());
            result.setRequestType("Advisory");
            return result;
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Body: binary
    public LPMobileHttpResponse sendScreenShotPostRequest(VisitIntroResponse visitIntroResponse, String postBody) throws IOException {
        return chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "screenshot/" + introChatResponse.getEngagementId());
    }

    //Example: {"permission":"revoked", “asset”:”screenshare”} , {"permission":"granted", “asset”:”screenshare”}
    public LPMobileHttpResponse sendPermissionPostRequest(VisitIntroResponse visitIntroResponse, String perm, String asset) throws IOException {
        Permission permission = new Permission();
        permission.setPermission(perm);
        permission.setAsset(asset);
        try {
            String postBody = jsonMarshaller.marshalObj(permission, Class.forName("json.model.Permission"));
            return chatConnectionHandler.postRequest(visitIntroResponse, postBody, introChatResponse, "permission/" + introChatResponse.getEngagementId());
        } catch (ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
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
