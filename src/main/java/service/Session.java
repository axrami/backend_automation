package service;


import json.model.AppSettings;
import json.model.LPMobileEnvironment;
import json.model.VisitIntroResponse;
import model.*;
import networking.ContinueRequestHandler;
import networking.VisitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.chat.ChatHandler;

/**
 * Created by andrew on 7/21/15.
 */
public class Session {
    private Visitor visitor;
    public VisitIntroResponse visitIntroResponse;
    private LPMobileEnvironment env;
    private AppSettings appSettings;
    public VisitHandler visitHandler = new VisitHandler();
    public ChatHandler chat = new ChatHandler();
    public Logger logger = LoggerFactory.getLogger("Session");

    public Session() {
        this.appSettings = SetEnvironment.createAppSettings();
        this.env = SetEnvironment.createBaseEnv();
        this.visitor = new Visitor();
    }

    public Session(LPMobileEnvironment env, AppSettings appSettings, Visitor visitor) {
        this.env = env;
        this.appSettings = appSettings;
        this.visitor = visitor;
    }

    public VisitHandler beginVisit() {
        visitIntroResponse = visitHandler.launch(env, appSettings, visitor);
        return visitHandler;
    }

    public VisitHandler continueVisit() {
        visitHandler.continueVisit();
        return visitHandler;
    }

    public ChatHandler beginChat() {
        chat.createConnection(env, visitIntroResponse, visitor);
        return chat;
    }

    public void getChatHistory() {

    }

    public void setCustomVars() {

    }

    public void sendAdvisory() {

    }

}
