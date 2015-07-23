package service;


import model.*;
import networking.ContinueRequestHandler;
import networking.VisitHandler;
import service.chat.ChatHandler;

/**
 * Created by andrew on 7/21/15.
 */
public class Session {
    private Visitor visitor = new Visitor();
    private LPMobileVisit visit;
    private LPMobileEnvironment env;

    public void setEnv(LPMobileEnvironment env) {
        this.env = env;
    }

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
        ChatHandler chat = new ChatHandler();
        chat.createConnection(env, visit, visitor);

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
