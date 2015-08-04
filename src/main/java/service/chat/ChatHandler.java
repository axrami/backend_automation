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

//    public void sendLine() {
//        try {
//            String postBody = JsonGenerator.generateChatLineReqeust("Hello");
//            HttpResponse response = chatConnectionHandler.sendLinePostRequest(visit, postBody, visitor, introChatResponse);
//            logger.debug("<sendLine> response " + response.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendLine() {
        try {
            String suffix = "line/";
            String postBody = JsonGenerator.generateChatLineReqeust("Hello");
            HttpResponse response = chatConnectionHandler.sendPost(visit, visitor, postBody, introChatResponse, suffix);
            logger.debug("<sendLine> response " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLine(String text) {
        try {
            String postBody = JsonGenerator.generateChatLineReqeust(text);
            HttpResponse response = chatConnectionHandler.sendLinePostRequest(visit, postBody, visitor, introChatResponse);
            logger.debug("<sendLine> response " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void endChat() {
//        try {
//            HttpResponse response = chatConnectionHandler.sendOutroPostReqeust(visit, "", visitor, introChatResponse);
//            logger.debug("<sendOutro> response " + response.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void endChat() {
        try {
            String suffix = "outro/";
            String postBody = "";
            HttpResponse response = chatConnectionHandler.sendPost(visit, visitor, postBody, introChatResponse, suffix );
            logger.debug("<endChat> response" + response);
        } catch (IOException e ) {
            e.printStackTrace();
        }
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
