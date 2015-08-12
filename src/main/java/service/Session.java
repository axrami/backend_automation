package service;


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
    private Visitor visitor = new Visitor();
    public LPMobileVisit visit;
    private LPMobileEnvironment env;
    ChatHandler chat = new ChatHandler();
    public void setEnv(LPMobileEnvironment env) {
        this.env = env;
    }
    public Logger logger = LoggerFactory.getLogger("Session");

    public void beginVisit() {
        if (this.env == null) {
            this.env = SetEnvironment.createBaseEnv();
        }
        VisitHandler visitHandler = new VisitHandler();
        visitHandler.launch(env , visitor);
        this.visit = visitHandler.getVisit();
    }

    public void beginVisit(LPMobileEnvironment env) {
        VisitHandler visitHandler = new VisitHandler();
        visitHandler.launch(env, visitor);
        this.visit = visitHandler.getVisit();
    }


    public void continuedVisit() {
        if (this.visit == null) {
            beginVisit();
        }
        ContinueRequestHandler c = new ContinueRequestHandler();
        c.launchContinue(env, visit, visitor);

    }

    public void beginChat() {
        if (this.visit == null ) {
            beginVisit();
        }
        chat.createConnection(env, visit, visitor);
    }


    public void getChatHistory() {

    }

    public void setCustomVars() {

    }

    public void sendAdvisory() {

    }

    public LPMobileEnvironment getEnv() {
        return env;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public LPMobileVisit getVisit() {
        return visit;
    }
}
