package service;


import json.model.AppSettings;
import json.model.LPMobileEnvironment;
import json.model.VisitIntroResponse;
import model.*;
import networking.VisitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import properties.LPMobileConfig;
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
    private LPMobileConfig config = LPMobileConfig.getInstance();
    public Logger logger = LoggerFactory.getLogger("Session");

    public Session() {
        this.appSettings = SetEnvironment.createAppSettings();
        this.env = SetEnvironment.createBaseEnv();
        this.visitor = new Visitor();
    }

    public Session(AppSettings appSettings, Visitor visitor) {
        this.appSettings = appSettings;
        if (visitor != null) {
            this.visitor = visitor;
        } else {
            this.visitor = new Visitor();
        }
    }

    public void setConfig(String env, int apiVersion, boolean isDebug) {
        config.setEnvironment(env);
        config.setApiVersion(apiVersion);
        config.setIsDebug(isDebug);
        config.setBaseUrls();
    }

    public VisitHandler beginVisit() {
        this.visitIntroResponse = visitHandler.launch(appSettings, visitor);
        return visitHandler;
    }

    public VisitHandler continueVisit() {
        visitHandler.continueVisit();
        return visitHandler;
    }

    public ChatHandler beginChat() {
        logger.debug("LOGGED FROM SESSION VISIT ID " + visitIntroResponse.getVisit_id());
        chat.createConnection(appSettings, visitIntroResponse, visitor);
        return chat;
    }

    public void getChatHistory() {

    }

    public void setCustomVars() {

    }

    public void sendAdvisory() {

    }

}
